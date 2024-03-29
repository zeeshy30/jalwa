import ProductSKUModel from './productSKU';
import ProductModel from '../product/product';

const productSKUs = {
    name: 'productSKUs',
    args: {
        handle: 'String!'
    },
    type: 'ProductSKUResult!',
    resolve: async ({ args }) => {
        try {
            const producSKUList = await ProductSKUModel.find({ handle: args.handle });
            if (producSKUList.length === 0) {
                return {
                    __typename: 'Error',
                    statusCode: 404,
                    message: 'SKUs not found for the product'
                };
            }
            return {
                __typename: 'ProductSKUs',
                productSKUs: producSKUList
            };

        }
        catch (error) {
            return Promise.reject(error);
        }
    } 
};

const addProductSKU = {
    name: 'addProductSKU',
    type: 'Status!',
    args: {
        handle: 'String!',
        SKU: 'String!',
        variantType1: 'String',
        variant1: 'String',
        variantType2: 'String',
        variant2: 'String',
        quantity: 'Int!',
    },
    resolve: async ({ args }) => {
        const { handle, SKU, variantType1='', variant1='', variantType2='', variant2='', quantity  } = args;
        try {
            const handleExists = await ProductModel.findOne({ handle });
            if (!handleExists) {
                return {
                    __typename: 'Error',
                    statusCode: 404,
                    message: `Invalid Product handle, ${handle}`
                };
            }
            const SKUexists = await ProductSKUModel.findOne({ SKU });
            if (SKUexists) {
                return {
                    __typename: 'Error',
                    statusCode: 409,
                    message: `${SKU} already exists`
                };
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

            return {
                __typename: 'Error',
                succeed: true,
            };
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
