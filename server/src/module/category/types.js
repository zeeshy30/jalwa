import { schemaComposer } from 'graphql-compose';
import { composeWithMongoose } from 'graphql-compose-mongoose';

import CategoryModel from './category';
import { error } from '../../graphql/types';

const CategoryTC = composeWithMongoose(CategoryModel);

const CategoriesTC = schemaComposer.createObjectTC({
    name: 'Categories',
    fields: {
        categories: [CategoryTC]
    }
});
 
schemaComposer.createUnionTC({
    name: 'CategoriesResult',
    types: [ error, CategoriesTC ],
});

export default CategoriesTC;
