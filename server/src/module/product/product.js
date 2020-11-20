import mongoose from 'mongoose';

const { Schema } = mongoose;

const productSchema = new Schema(
    {
        handle: {
            type: String,
            required: true,
        },
        title: {
            type: String,
            required: true,
        },
        body: String,
        vendor: {
            type: String,
            required: true,
        },
        type: String,
        tags: [String],
        videoUrl :{
            type: String,
            required: true,
        },
    },
    { timestamps: true }
);

const Product = mongoose.model('Product', productSchema);

export default Product;
