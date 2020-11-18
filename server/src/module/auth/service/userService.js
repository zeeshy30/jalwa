import crypto from 'crypto-random-string';
import moment from 'moment';

class UserService {
    verifyRequest = async (user) => {
        const token = crypto({ length: 48, type: 'url-safe' });
        const expiresIn = moment().add(100, 'days');

        user.set({
            account: {
                verification: {
                    token,
                    expiresIn
                }
            }
        });

        await user.save();

        return token;
    }

    static getInstance () {
        if (!this.instance) {
            this.instance = new this();
        }
        return this.instance;
    }
}

export default UserService;
