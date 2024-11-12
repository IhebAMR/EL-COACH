import { Column, Entity, PrimaryGeneratedColumn, Unique } from "typeorm";

@Entity()
export class User {
    @PrimaryGeneratedColumn()
    id: number;
    @Column()
    fullName: string;
    @Column({ unique: true })
    email: string;
    @Column()
    password: string;
    @Column({default:true})
    isActive: boolean;

}
