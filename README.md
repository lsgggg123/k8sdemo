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

pod 的端口转发到 mac 端口（docker for mac desktop 使用)
```
kubectl port-forward pods/k8sdemo-pod -n k8sdemo-namespace 8088:8088
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

验证共享宿主机目录：
```
kubectl apply -f yaml/pod/1-share-host-volums.yaml
kubectl get pods -n k8sdemo-namespace
kubectl exec -it -n k8sdemo-namespace two-containers -c busybox-container /bin/sh
```
进入 busybox 的容器中，wget 127.0.0.1/index.html，可以看到共享的路径生效

init container 
```
kubectl apply -f yaml/pod/2-init-container.yaml
kubectl get pod init-pod  -n k8sdemo-namespace 
kubectl logs init-pod -n k8sdemo-namespace
```

共享 pid
```
kubectl apply -f yaml/pod/3-share-pid.yaml
kubectl exec -it pid-share -c busybox-shell -n k8sdemo-namespace /bin/sh
ps -ef
```
可以看到，通过 `shareProcessNamespace: true`，busybox 镜像里面可以看到 nginx 的 pid

除了 pid 的 shareProcessNamespace，还有可以共享宿主机的资源：
```

spec:
  hostNetWork: true
  hostIPC: true
  hostPID: true
```
liveness

```
kubectl apply yaml/pod/7-liveness.yaml
kubectl get pod -n k8sdemo-namespace
NAME                 READY   STATUS    RESTARTS   AGE
test-liveness-exec   1/1     Running   1          113s

```
### secret
```
kubectl apply -f yaml/secret/0-test-secret.yaml 

kubectl apply -f yaml/secret/1-use-secret.yaml

kubectl exec -it use-secret -n k8sdemo-namespace /bin/sh

cd /secret-volume
cat user
cat pass
```

### config map
```
kubectl create configmap bootstrap-configmap -n k8sdemo-namespace --from-file=src/main/resources/bootstrap.properties 

kubectl apply -f yaml/configmap/0-use-configmap.yaml 

kubectl exec -it use-configmap -n k8sdemo-namespace /bin/sh

cat /config-volume/bootstrap.properties
```

### downward api
```
kubectl apply -f yaml/downwardapi/0-downwardapi.yaml 

kubectl exec -it test-downwardapi -n k8sdemo-namespace /bin/sh

cat /etc/podinfo/labels
```

### deployment
```
kubectl apply -f yaml/deployment/nginx-deployment.yaml

kubectl get pods -l app=nginx -n k8sdemo-namespace
kubectl get rs  -n  k8sdemo-namespace

kubectl describe pod nginx-deployment-xxx-xxx -n k8sdemo-namespace 

kubectl delete deployment nginx-deployment -n k8sdemo-namespace [kubectl delete -f yaml/deployment/nginx-deployment.yaml]

kubectl scale deployment nginx-deployment --replicas=3
```

直接编辑 yaml
```
kubectl edit deployment/nginx-deployment
```

### service
```
kubectl apply -f yaml/service/0-service.yaml 
kubectl apply -f yaml/service/1-web-deployment.yaml 
```

进入 pod，验证域名解析成功（域名：<SERVICE_NAME>.<NAMESPACE_NAME>）
```
kubectl exec -it podname-xxxx -n k8sdemo-namespace /bin/bash
curl web-service.k8sdemo-namespace:8081/k8sdemo/api/v1/web
```