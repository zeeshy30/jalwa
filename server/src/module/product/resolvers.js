import ProductModel from './product';

const products = {
    name: 'products',
    type: ['Product!'],
    resolve: async () => {
        const productList = await ProductModel.find({});
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
        type: 'String',
        tags: ['String'],
        price: 'String!',
        photoUrl: 'String!',
        videoUrl: 'String!',
    },
    resolve: async ({ args }) => {
        const { handle, title, body = '', vendor, type = '', tags = [], price, photoUrl, videoUrl } = args;
        try {
            const handleExists = await ProductModel.findOne({ handle });
            if (handleExists) {
                return Promise.reject(new Error('This handle is already registered.'));
            }

            const product = await new ProductModel({
                handle, 
                title,
                body,
                vendor, 
                type,
                tags,
                price,
                photoUrl,
                videoUrl, 
            }).save();

            return 'succeed: true';
        }
        catch(error) {
            return Promise.reject(error);   
        }
    }
};

export default {
    products,
    addProduct
};
