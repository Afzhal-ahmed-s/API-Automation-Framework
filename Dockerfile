# Use an official Maven + JDK image as the base
FROM maven:3.9.6-eclipse-temurin-17

# Set time zone to Asia/Kolkata (IST)
ENV TZ=Asia/Kolkata
RUN apt-get update && \
    apt-get install -y tzdata && \
    ln -fs /usr/share/zoneinfo/$TZ /etc/localtime && \
    dpkg-reconfigure -f noninteractive tzdata

# Set working directory inside the container
WORKDIR /app

# Copy everything from current project directory into container
COPY . .

# Build the project and run tests
CMD ["mvn", "clean", "test", "-Dsurefire.useFile=false"]
