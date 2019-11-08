## usage
```
mvn clean package
docker build -t k8sdemo:v1 .
docker run -d -p 8088:8088  k8sdemo:v1
```

## resource
### namespace

create namespace

```
apiVersion: v1
kind: Namespace
metadata:
  name: k8sdemo
```

list all namespace
```
kubectl get namespaces
```

delete namespace
```
kubectl delete namespace k8sdemo
```