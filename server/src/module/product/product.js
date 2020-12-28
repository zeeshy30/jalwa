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
        category: {
            type: String,
            required: true,
        },
        tags: [String],
        price: {
            type: String,
            required: true,
        },
        photoUrl: {
            type: String,
            required: true,
        },
        videoUrl: {
            type: String,
            required: true,
        },
    },
    { timestamps: true }
);

const Product = mongoose.model('Product', productSchema);

export default Product;
