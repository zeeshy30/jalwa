import CategoryTC from './types';
import resolvers from './resolvers';

Object.keys(resolvers).forEach((resolver) => {
    CategoryTC.addResolver(resolvers[resolver]);
});

export default CategoryTC;
