FROM node:21-alpine AS builder
WORKDIR /opt/swe2023brown/frontend
COPY frontend/package.json package.json
COPY frontend/package-lock.json package-lock.json
RUN npm install
COPY frontend .
RUN npm run build

FROM nginx:stable-alpine
COPY --from=builder /opt/swe2023brown/frontend/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /opt/swe2023brown/frontend/build /usr/share/nginx/html
EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]
