FROM node:latest

WORKDIR /app

COPY . /app

RUN npm install
RUN npm run build

EXPOSE 5173

CMD ["npm", "run", "dev"]