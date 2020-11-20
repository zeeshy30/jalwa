import ProductTC from './types';
import resolvers from './resolvers';

Object.keys(resolvers).forEach((resolver) => {
    ProductTC.addResolver(resolvers[resolver]);
});

export default ProductTC;
