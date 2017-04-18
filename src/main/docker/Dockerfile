FROM java:8
VOLUME /tmp
RUN mkdir /app
ADD admin-1.0.1.jar /app/admin.jar
ADD runboot.sh /app/
RUN bash -c 'touch /app/admin.jar'
WORKDIR /app
RUN chmod a+x runboot.sh
EXPOSE 8080
CMD /app/runboot.sh
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezoneorg