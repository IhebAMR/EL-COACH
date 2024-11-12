import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import { UserModule } from './user/user.module';
import { User } from './user/entities/user.entity';
import { ConfigModule, ConfigService } from '@nestjs/config';

import { JwtModule } from '@nestjs/jwt';
import { AuthModule } from './auth/auth.module';
import config from './config/config';
import { RefreshToken } from './refresh-token/refresh-token';
@Module({
  imports: [ConfigModule.forRoot({
    isGlobal: true,
    cache: true,
    load: [config],
  }),
  JwtModule.registerAsync({
    imports: [ConfigModule],
    useFactory: async (config) => ({
      secret: config.get('jwt.secret'),
    }),
    global: true,
    inject: [ConfigService],
  }),  
  TypeOrmModule.forRoot({
    type: 'mysql',
    host: 'localhost',
    port: 3306,
    username: 'root',
    password: '',
    database: 'El Coach',
    entities: [User,RefreshToken],
    synchronize: false,
    autoLoadEntities: true,
    
  }), UserModule, AuthModule
],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
