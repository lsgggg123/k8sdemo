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
  labels:
    name: k8s-learn-demo
```
```
kubectl apply -f yaml/namespace/k8sdemo-namespace.yaml
```


list all namespace
```
kubectl get namespaces [--show-labels]
```

delete namespace
```
kubectl delete namespace k8sdemo
```

### pod

```
apiVersion: v1
kind: Pod
metadata:
  name: k8sdemo-pod
  namespace: k8sdemo-namespace
spec:
  hostNetwork: true
  containers:
    - name: k8sdemo-container
      image: k8sdemo:v1
      ports:
        - containerPort: 8088
          hostPort: 8088
```
```
kubectl apply -f yaml/pod/0-k8sdemo-pod.yaml 
```

list pod
```
kubectl get pods -n k8sdemo-namespace
```

进入 pod
```
kubectl exec -it k8sdemo-pod -n k8sdemo-namespace /bin/bash
```

delete pod
```
kubectl delete pod k8sdemo-pod -n k8sdemo-namespace
```

查看 pod 在宿主机进程上的 pid
```
docker inspect --format '{{ .State.Pid }}' containerId

ls -l /proc/pid/ns
```

### deployment
```
kubectl apply -f yaml/deployment/nginx-deployment.yaml

kubectl get pods -l app=nginx -n k8sdemo-namespace

kubectl describe pod nginx-deployment-xxx-xxx -n k8sdemo-namespace 

kubectl delete deployment nginx-deployment -n k8sdemo-namespace [kubectl delete -f yaml/deployment/nginx-deployment.yaml]
```