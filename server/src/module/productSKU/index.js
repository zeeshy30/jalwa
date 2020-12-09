import ProductSKUTC from './types';
import resolvers from './resolvers';

Object.keys(resolvers).forEach((resolver) => {
    ProductSKUTC.addResolver(resolvers[resolver]);
});

export default ProductSKUTC;
