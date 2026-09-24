import type User from "./User";

export default interface loginResponseData {
   
     accessToken:string,
     expiresIn: number,
     tokenType: string
     userDto: User,
}