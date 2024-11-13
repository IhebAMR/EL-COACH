import { Module } from '@nestjs/common';
import { AuthService } from './auth.service';
import { AuthController } from './auth.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { User } from 'src/user/entities/user.entity';
import { RefreshToken } from 'src/refresh-token/refresh-token';
import { ResetToken } from 'src/reset-token/reset-token';
import { MailService } from 'src/services/mail.service';

@Module({
  imports: [
    TypeOrmModule.forFeature([User, RefreshToken,ResetToken]),  // Register entities here
  ],
  controllers: [AuthController],
  providers: [AuthService, MailService],
  exports: [AuthService],
})
export class AuthModule {}
