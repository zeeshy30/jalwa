import mongoose from 'mongoose';

const { Schema } = mongoose;

const productSKUSchema = new Schema(
    {
        SKU: {
            type: String,
            required: true,
        },
        handle: {
            type: String,
            required: true,
        },
        size: String,
        color: String,
        quantity: {
            type: Number,
            required: true,
        }
    },
    { timestamps: true }
);

const ProductSKU = mongoose.model('ProductSKU', productSKUSchema);

export default ProductSKU;
