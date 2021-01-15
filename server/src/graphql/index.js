import { graphqlHTTP } from 'express-graphql';
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
        }
    });
});
