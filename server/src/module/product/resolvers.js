import merge from 'deepmerge';
import ProductModel from './product';
import CategoryModel from '../category/category';

const productsFilteredByCategory = {
    name: 'productsFilteredByCategory',
    type: ['Product!'],
    args: {
        category: 'String!'
    },
    resolve: async ({ args }) => {
        const { category } = args;
        let productList = null;
        if (category === 'All') {
            productList = await ProductModel.find({});
        } else {
            productList = await ProductModel.find({ category });
        }
        return productList;    
        
    } 
};

const addProduct = {
    name: 'addProduct',
    type: 'String!',
    args: {
        handle: 'String!',
        title: 'String!',
        body: 'String',
        vendor: 'String!',
        category: 'String!',
        tags: ['String'],
        price: 'String!',
        photoUrl: 'String!',
        videoUrl: 'String!',
    },
    resolve: async ({ args }) => {
        const { handle, title, body = '', vendor, category, tags = [], price, photoUrl, videoUrl } = args;
        try {
            const handleExists = await ProductModel.findOne({ handle });
            if (handleExists) {
                return Promise.reject(new Error('This handle is already registered.'));
            }

            await new ProductModel({
                handle, 
                title,
                body,
                vendor, 
                category,
                tags,
                price,
                photoUrl,
                videoUrl, 
            }).save();
            
            const categoryExists = await CategoryModel.findOne({ category });
            if (!categoryExists) {
                await new CategoryModel({
                    category
                }).save();
            }

            return 'succeed: true';
        }
        catch(error) {
            return Promise.reject(error);   
        }
    }
};

const updateProduct = {
    name: 'updateProduct',
    type: 'String!',
    args: {
        _id: 'String!',
        title: 'String',
        body: 'String',
        vendor: 'String',
        category: 'String',
        tags: ['String'],
        price: 'String',
        photoUrl: 'String',
        videoUrl: 'String',
    },
    resolve: async ({ args }) => {
        const { _id, title=null, body=null, vendor=null, category=null, tags=[], price=null, photoUrl=null, videoUrl=null } = args;
        try {
            const product = await ProductModel.findById(_id);
            if (!product) {
                return Promise.reject(new Error('Invalid product ID.'));
            }

            Object.assign(product, merge(product.toObject(), args));
            await product.save();
            return 'succeed: true';
        }
        catch(error) {
            return Promise.reject(error);   
        }
    }
};

export default {
    productsFilteredByCategory,
    addProduct,
    updateProduct
};
