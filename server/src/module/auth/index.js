import UserTC from './types';
import resolvers from './resolvers';

Object.keys(resolvers).forEach((resolver) => {
    UserTC.addResolver(resolvers[resolver]);
});

export default UserTC;
