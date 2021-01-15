import { schemaComposer } from 'graphql-compose';
import { composeWithMongoose } from 'graphql-compose-mongoose';

import ProductModel from './product';
import { error } from '../../graphql/types';

const ProductTC = composeWithMongoose(ProductModel);

const productsTC = schemaComposer.createObjectTC({
    name: 'Products',
    fields: {
        products: [ProductTC]
    }
});

 
schemaComposer.createUnionTC({
    name: 'ProductsResult',
    types: [ error, productsTC ],
});

export default productsTC;
