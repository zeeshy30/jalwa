import { schemaComposer } from 'graphql-compose';
import { composeWithMongoose } from 'graphql-compose-mongoose';
import { error } from '../../graphql/types';

import ProductSKUModel from './productSKU';

const ProductSKUTC = composeWithMongoose(ProductSKUModel);


const ProductSKUsTC = schemaComposer.createObjectTC({
    name: 'ProductSKUs',
    fields: {
        productSKUs: [ProductSKUTC]
    }
});
 
schemaComposer.createUnionTC({
    name: 'ProductSKUResult',
    types: [ error, ProductSKUsTC ],
});

export default ProductSKUsTC;
