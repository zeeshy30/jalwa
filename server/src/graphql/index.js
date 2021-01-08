import { graphqlHTTP } from 'express-graphql';
import { printLocation } from 'graphql';
import schema from './schema';

export default graphqlHTTP(async request => {
    return ({
        schema,
        graphiql: true,
        debug: false,
        context: {
            user: request.user,
            headers: request.headers,
            accessToken: request.accessToken,
        },
        customFormatErrorFn: (err) => {
            console.log('errrr:', err);
            return ({ message: err.message });
        }
    });
});
