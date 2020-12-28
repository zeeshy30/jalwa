import ProductSKUModel from './productSKU';
import ProductModel from '../product/product';

const productSKUs = {
    name: 'productSKUs',
    args: {
        handle: 'String!'
    },
    type: ['ProductSKU!'],
    resolve: async ({ args }) => {
        const producSKUList = await ProductSKUModel.find({ handle: args.handle });
        return producSKUList;
    } 
};

const addProductSKU = {
    name: 'addProductSKU',
    type: 'String!',
    args: {
        handle: 'String!',
        SKU: 'String!',
        variantType1: 'String!',
        variant1: 'String!',
        variantType2: 'String',
        variant2: 'String',
        quantity: 'Int!',
    },
    resolve: async ({ args }) => {
        const { handle, SKU, variantType1, variant1, variantType2='', variant2='', quantity  } = args;
        try {
            const handleExists = await ProductModel.findOne({ handle });
            if (!handleExists) {
                return Promise.reject(new Error('This handle does not exist.'));
            }
            const SKUexists = await ProductSKUModel.findOne({ SKU });
            if (SKUexists) {
                return Promise.reject(new Error('This SKU already exists.'));
            }

            await new ProductSKUModel({
                handle,
                SKU,
                variantType1,
                variant1,
                variantType2,
                variant2,
                quantity,
            }).save();

            return 'succeed: true';
        }
        catch(error) {
            return Promise.reject(error);   
        }
    }
};


export default {
    productSKUs,
    addProductSKU
};
