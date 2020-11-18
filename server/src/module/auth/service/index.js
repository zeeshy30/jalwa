import UserService from './userService';

const UserServiceObject =  UserService.getInstance().verifyRequest;
const verifyRequestService = UserServiceObject.verifyRequest;

export default verifyRequestService;
