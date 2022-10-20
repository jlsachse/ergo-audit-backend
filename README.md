# ErgoAudit Backend
ErgoAudit is an open-source platform to audit projects around and on the Ergo
Platform. Anyone with a web3 experience can analyze an existing project in the
ecosystem. Dao provides templates for analyzing projects in various categories, 
such as defi, metaverse, nft, mining, dao and others.

## Links
- [Website](https://red-lobster-showcase.link/)
- [Frontend](https://github.com/jlsachse/ergo-audit-frontend)
- [OpenAPI](https://red-lobster-showcase.link/docs)

## Docker

Our backend can be easily installed with docker.
Just clone the repository and run the commands below.

```sh
cd /ergo-audit-backend
docker build -t ergoauditbackend .
docker container run -d -p [YOUR_PORT]:8080 --env MONGODB_URI=[MONGO_CONNECTION_STRING] --env MONGODB_DB=[DATABASE] ergoauditbackend
```