import express, { json } from 'express';
import cors from 'cors';
import mongoose from 'mongoose';
import winston from 'winston';
import dotenv from 'dotenv';
import path from 'path';
import authentication from './middleware/authentication';
import graphql from './graphql';

const root = path.join.bind(this, __dirname, '../');
dotenv.config({ path: root('.env') });

const app = express();

mongoose
    .connect(`${process.env.DB_HOST}:${process.env.DB_PORT}/${process.env.DB_NAME}`, {
        useNewUrlParser: true,
        useUnifiedTopology: true
    })
    .catch(error => winston.error(error));

app.use(
    '/graphql',
    json(),
    cors({
        origin: process.env.CLIENT_URL,
        optionsSuccessStatus: 200
    }),
    authentication,
    graphql
);

app.use('*', (req, res) => {
    res.status(404)
        .send('404 Not Found');
});
app.listen(process.env.APP_PORT);
