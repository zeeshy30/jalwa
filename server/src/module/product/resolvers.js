import ProductModel from './product';

const products = {
    name: 'products',
    type: ['Product!'],
    resolve: async () => {
        const productList = await ProductModel.find({});
        return productList;
    } 
};

const addProduct = {
    name: 'addProduct',
    type: 'String!',
    args: {
        handle: 'String!',
        title: 'String!',
        body: 'String',
        vendor: 'String!',
        type: 'String',
        tags: ['String'],
        videoUrl: 'String!',
    },
    resolve: async ({ args }) => {
        const { handle, title, body = '', vendor, type = '', tags = [], videoUrl } = args;
        try {
            const handleExists = await ProductModel.findOne({ handle });
            if (handleExists) {
                return Promise.reject(new Error('This handle is already registered.'));
            }

            const product = await new ProductModel({
                handle, 
                title,
                body,
                vendor, 
                type,
                tags,
                videoUrl, 
            }).save();
            console.log(product, 'savedddd');
            return 'succeed: true';
        }
        catch(error) {
            return Promise.reject(error);   
        }
    }
};




// const updateProduct = {
//     name: 'updateProduct',
//     type: 'User!',
//     args: { fullName: 'String!',  },
//     resolve: async ({ args: { fullName }, context: { user } }) => {
//         try {
//             user.set({
//                 fullName
//             });

//             await user.save();

//             return user;
//         } catch (error) {
//             return Promise.reject(error);
//         }
//     }
// };

// const signIn = {
//     name: 'signIn',
//     type: 'AccessToken!',
//     args: {
//         phoneNumber: 'String!',
//         code: 'String!'
//     },
//     resolve: async ({ args: { phoneNumber, code } }) => {
//         try {
//             const user = await UserModel.numberExist(phoneNumber);
//             if (!user) {
//                 return Promise.reject(new Error('User not found.'));
//             }

//             const res = await verifyCode(phoneNumber, code);
//             if (res.status !== 'approved') {
//                 return Promise.reject(new Error('Invalid code provided'));
//             }

//             const accessToken = jwt.sign(
//                 { userId: user._id },
//                 process.env.JWT_SECRET,
//             );

//             user.account = {
//                 verification: {
//                     verified: true,
//                     token: accessToken,
//                 },
//             };
//             user.save();

//             return { accessToken };
//         } catch (error) {
//             return Promise.reject(error);
//         }
//     }
// };

// const requestCode = {
//     name: 'requestCode',
//     type: 'String!',
//     args: {
//         phoneNumber: 'String!',
//     },
//     resolve: async ({ args: { phoneNumber } }) => {
//         try {
//             let user = await UserModel.numberExist(phoneNumber);
//             await sendVerificationCode(phoneNumber);

//             if (!user) {
//                 user = await new UserModel({
//                     phoneNumber,
//                 }).save();
//             }

//             return 'success!';
//         } catch (error) {
//             return Promise.reject(error);
//         }
//     }
// };

// const signUp = {
//     name: 'signUp',
//     type: 'AccessToken!',
//     args: {
//         phoneNumber: 'String!',
//         code: 'String!'
//     },
//     resolve: async ({ args: { phoneNumber, code } }) => {
//         try {
//             const user = await UserModel.numberExist(phoneNumber);
//             if (user && user.account.verification.verified) {
//                 return Promise.reject(new Error('This number has already been registered.'));
//             }

//             const res = await verifyCode(phoneNumber, code);
//             if (res.status !== 'approved') {
//                 return Promise.reject(new Error('Invalid code provided'));
//             }

//             const accessToken = jwt.sign(
//                 { userId: user._id },
//                 process.env.JWT_SECRET,
//             );

//             user.account = {
//                 verification: {
//                     verified: true,
//                     token: accessToken,
//                 },
//             };

//             user.save();

//             return { accessToken };
//         } catch (error) {
//             return Promise.reject(error);
//         }
//     }
// };

// const logout = {
//     name: 'logout',
//     type: 'Succeed!',
//     resolve: async ({ context: { user, accessToken } }) => {
//         // try {
//         //     await redis.set(
//         //         `expiredToken:${accessToken}`,
//         //         user._id,
//         //         'EX',
//         //         process.env.REDIS_TOKEN_EXPIRY
//         //     );

//         //     return { succeed: true };
//         // } catch (error) {
//         //     return Promise.reject(error);
//         // }
//         return { succeed: true };
//     }
// };


// const updateUser = {
//     name: 'updateUser',
//     type: 'User!',
//     args: { fullName: 'String!' },
//     resolve: async ({ args: { fullName }, context: { user } }) => {
//         try {
//             user.set({
//                 fullName
//             });

//             await user.save();

//             return user;
//         } catch (error) {
//             return Promise.reject(error);
//         }
//     }
// };

export default {
    products,
    addProduct
};
