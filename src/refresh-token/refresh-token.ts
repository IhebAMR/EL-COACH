import { Entity, Column, PrimaryGeneratedColumn, CreateDateColumn, UpdateDateColumn } from 'typeorm';

@Entity()
export class RefreshToken {
  @PrimaryGeneratedColumn('uuid')  // Generates a unique ID as a UUID, similar to MongoDB's ObjectId
  id: number;

  @Column({ type: 'varchar', length: 255, nullable: false })
  token: string;

  @Column({ type: 'int', nullable: false })
  userId: number;  // Assuming userId is a numeric ID from a related `User` table

  @Column({ type: 'timestamp', nullable: false })
  expiryDate: Date;

  @CreateDateColumn()
  createdAt: Date;

  @UpdateDateColumn()
  updatedAt: Date;
}
