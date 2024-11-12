import { Injectable } from '@nestjs/common';
import { CreateUserDto } from './dto/create-user.dto';
import { UpdateUserDto } from './dto/update-user.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { User } from './entities/user.entity';
import { Repository } from 'typeorm';

@Injectable()
export class UserService {
  constructor(
    @InjectRepository(User)
    private usersRepository: Repository<User>,
  ) {}

  async add(createUserDto: CreateUserDto): Promise<User> {
    const user = await this.usersRepository.findOneBy({ email: createUserDto.email });
    
    if (user) {
      throw new Error('email already exists');  // Throw a custom error
    }

    const newUser = this.usersRepository.create(createUserDto);
    return await this.usersRepository.save(newUser);
  }

    async update(id: number, updateUserDto: UpdateUserDto): Promise<User> {
      return this.usersRepository.save({ id, ...updateUserDto });
    }
  async findAll(): Promise<User[]> {
    return this.usersRepository.find();
  }

  async findOne(id: number): Promise<User | null> {
    return this.usersRepository.findOneBy({ id });

  }
  async findOnebyEmail(email: string): Promise<User | null> {
    return this.usersRepository.findOneBy({ email });

  }

  async remove(id: number): Promise<void> {
    await this.usersRepository.delete(id);
  }
}
