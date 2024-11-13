import { Entity, PrimaryGeneratedColumn, Column, CreateDateColumn } from 'typeorm';

@Entity()
export class ResetToken {
  @PrimaryGeneratedColumn('uuid')
  id: string;

  @Column({ nullable: false })
  token: string;

  @Column({ type: 'uuid', nullable: false })
  userId: number;

  @Column({ type: 'timestamp', nullable: false })
  expiryDate: Date;

  @CreateDateColumn()
  createdAt: Date;

  @CreateDateColumn({ onUpdate: 'CURRENT_TIMESTAMP' })
  updatedAt: Date;
}
