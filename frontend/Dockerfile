FROM node:latest

WORKDIR /

COPY . .

RUN yarn add postcss-cli@latest
RUN yarn add react-scripts@latest
RUN yarn build

CMD ["yarn", "start"]

EXPOSE 3000