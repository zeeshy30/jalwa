import UserService from './userService';

const UserServiceObject =  UserService.getInstance();
export const verifyCodeService = UserServiceObject.verifyCode;
export const sendVerificationCodeService = UserServiceObject.sendVerificationCode;
