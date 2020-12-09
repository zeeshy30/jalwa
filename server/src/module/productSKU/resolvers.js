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
        size: 'String',
        color: 'String',
        quantity: 'Int!',
    },
    resolve: async ({ args }) => {
        const { handle, SKU, size='', color='', quantity  } = args;
        try {
            const handleExists = await ProductModel.findOne({ handle });
            if (!handleExists) {
                return Promise.reject(new Error('This handle does not exist.'));
            }

            const productSKU = await new ProductSKUModel({
                handle, 
                SKU,
                color,
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
