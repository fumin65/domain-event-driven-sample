# ドメインイベントによるイベント駆動のサンプル

## 概要

イベント駆動による非同期処理のサンプルコード。  
DDDのドメインイベントを用いてイベントの発生、配信を表現する。

## 環境

+ GAE/Java11
+ Cloud SQL for MySQL5.7
+ Cloud Pub/Sub

## アプリのローカル実行手順

ローカルで動作を確認するために、Pub/Subのローカルエミュレータ、MySQLのローカル環境を使用します。  
GAEにデプロイする場合は各種設定を変更してください。

### MySQLの環境構築

開発用にローカルにDockerでMySQL環境を構築します。
Dockerはインストール済み前提です。

`docker`ディレクトリの直下で、以下の順序でコマンドを実行します。

```
docker-compose build
docker-compose up -d
```

### Pub/Subエミュレータの起動

```
gcloud beta emulators pubsub start --project=local-project
```

### ローカル環境の初期化

```
$(gcloud beta emulators pubsub env-init)
./gradlew pubsub-emulator:run
```

### アプリの実行

環境変数の都合があるので、初期化を実行したコンソール上で以下を実行。

```
./gradlew app:runApp
```

以下のリクエストを実行

1. POST /api/projects {"name":"My Project"}
1. POST /api/columns {"projectId":"上のレスポンスのIDを入れる", "columnName":"Todo"}
1. POST /api/notes {"projectId":"上のレスポンスのIDを入れる", "columnId":"上のレスポンスのIDを入れる", "description":"コードレビューをする"}