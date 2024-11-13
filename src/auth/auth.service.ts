import {
  BadRequestException,
  Injectable,
  InternalServerErrorException,
  NotFoundException,
  UnauthorizedException,
} from '@nestjs/common';
import { SignupDto } from './dtos/signup.dto';
import * as bcrypt from 'bcrypt';
import { LoginDto } from './dtos/login.dto';
import { JwtService } from '@nestjs/jwt';
import { v4 as uuidv4 } from 'uuid';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository, MoreThanOrEqual } from 'typeorm';
import { User } from 'src/user/entities/user.entity';
import { RefreshToken } from 'src/refresh-token/refresh-token';
import { ResetToken } from 'src/reset-token/reset-token';
import { MailService } from 'src/services/mail.service';
import { nanoid } from 'nanoid';

@Injectable()
export class AuthService {
  constructor(
    @InjectRepository(User) private userRepository: Repository<User>,
    private jwtService: JwtService,
    @InjectRepository(RefreshToken) private refreshTokenRepo: Repository<RefreshToken>,
    @InjectRepository(ResetToken) private resetTokenRepo: Repository<ResetToken>,
    private mailService: MailService,
  ) {}

  async signup(signupData: SignupDto) {
    const { email, password, fullName } = signupData;

    const emailInUse = await this.userRepository.findOneBy({ email });
    if (emailInUse) {
      throw new BadRequestException('Email already in use');
    }

    const hashedPassword = await bcrypt.hash(password, 10);

    const newUser = this.userRepository.create({
      fullName,
      email,
      password: hashedPassword,
    });
    return await this.userRepository.save(newUser);
  }

  async login(credentials: LoginDto) {
    const { email, password } = credentials;

    const user = await this.userRepository.findOneBy({ email });
    if (!user) {
      throw new UnauthorizedException('Wrong credentials');
    }

    const passwordMatch = await bcrypt.compare(password, user.password);
    if (!passwordMatch) {
      throw new UnauthorizedException('Wrong credentials');
    }

    const tokens = await this.generateUserTokens(user.id);
    return {
      ...tokens,
      userId: user.id,
    };
  }

  async refreshTokens(refreshToken: string) {
    const token = await this.refreshTokenRepo.findOneBy({
      token: refreshToken,
      expiryDate: MoreThanOrEqual(new Date()),
    });

    if (!token) {
      throw new UnauthorizedException('Refresh Token is invalid');
    }

    return this.generateUserTokens(token.userId);
  }

  async generateUserTokens(userId: number) {
    const accessToken = this.jwtService.sign({ userId }, { expiresIn: '1D' });
    const refreshToken = uuidv4();

    await this.storeRefreshToken(refreshToken, userId);
    return {
      accessToken,
      refreshToken,
    };
  }

  async storeRefreshToken(token: string, userId: number) {
    const expiryDate = new Date();
    expiryDate.setDate(expiryDate.getDate() + 1);
  
    const existingToken = await this.refreshTokenRepo.findOneBy({ userId });
  
    if (existingToken) {
      // Update existing refresh token with new expiry date
      await this.refreshTokenRepo.update(existingToken.id, { token, expiryDate });
    } else {
      // Save a new refresh token
      await this.refreshTokenRepo.save({ userId, token, expiryDate });
    }
  }
  async changePassword(userId: number, oldPassword: string, newPassword: string) {
    // Find the user
    const user = await this.userRepository.findOne({ where: { id: userId } });
    if (!user) {
      throw new NotFoundException('User not found...');
    }
    const newHashedPassword = await bcrypt.hash(newPassword, 10);
    // Compare the old password with the password in DB
    const passwordMatch = await bcrypt.compare(oldPassword, user.password);
    if (!passwordMatch) {
      throw new UnauthorizedException('Wrong credentials');
    }

    // Change user's password
    
    user.password = newHashedPassword;
    await this.userRepository.save(user);
  }

  async forgotPassword(email: string) {
    // Check that user exists
    const user = await this.userRepository.findOne({ where: { email } });

    if (user) {
      // If user exists, generate password reset token
      const expiryDate = new Date();
      expiryDate.setHours(expiryDate.getHours() + 1);

      const resetToken = "token";
      const resetTokenEntity = this.resetTokenRepo.create({
        token: resetToken,
        userId: user.id,
        expiryDate,
      });

      await this.resetTokenRepo.save(resetTokenEntity);

      // Send the link to the user by email
      this.mailService.sendPasswordResetEmail(email, resetToken);
    }

    return { message: 'If this user exists, they will receive an email' };
  }

  async resetPassword(newPassword: string, resetToken: string) {
    // Find a valid reset token document
    const token = await this.resetTokenRepo.findOne({
      where: { token: resetToken, expiryDate: MoreThanOrEqual(new Date()), },
    });

    if (!token) {
      throw new UnauthorizedException('Invalid link');
    }

    // Change user password (MAKE SURE TO HASH!!)
    const user = await this.userRepository.findOne({ where: { id: token.userId } });
    if (!user) {
      throw new InternalServerErrorException();
    }

    user.password = await bcrypt.hash(newPassword, 10);
    await this.userRepository.save(user);

    // Optionally delete the used reset token
    await this.resetTokenRepo.delete({ id: token.id });
  }
  
  
  
  
}
