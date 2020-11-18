import AuthMiddleware from './authMiddleware';

const authMiddleWareObject = AuthMiddleware.getInstance();

export const { isAuth, isGuest, isUnverfied, isVerified } = authMiddleWareObject;
