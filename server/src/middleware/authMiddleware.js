class AuthMiddleware {
    isAuth = async (resolve, source, args, context, info) => {
        const { user } = context;

        if (!user) {
            return {
                status: {
                    statusCode: 401,
                    message: 'You must be authorized.'
                }
            };
        }

        return resolve(source, args, context, info);
    }
  
    isGuest = async (resolve, source, args, context, info) => {
        const { user } = context;
  
        if (user) {
            return {
                status: {
                    statusCode: 406,
                    message: 'You have already authorized.'
                }
            };
        }
        return resolve(source, args, context, info);
    }
  
    static getInstance () {
        if (!this.instance) {
            this.instance = new this();
        }
        return this.instance;
    }
}
  
export default AuthMiddleware;
  