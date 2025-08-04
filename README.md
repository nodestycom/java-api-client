<div align="center">
  <img src="https://nodesty.com/_ipx/s_140x32/nodestyGradient.png" alt="Nodesty Logo" width="300"/>
  <h1>Nodesty Java API İstemcisi</h1>
  <p><strong>nodesty.com</strong>'un güçlü API'sine Java uygulamalarınızdan kolayca erişmenizi sağlayan modern, asenkron ve tip-güvenli (type-safe) bir istemci kütüphanesi.</p>
  <p>
    <a href="https://www.java.com"><img src="https://img.shields.io/badge/Java-21-blue.svg" alt="Java 21"></a>
    <a href="https://github.com/nodestycom/java-api-client/blob/main/LICENSE"><img src="https://img.shields.io/badge/License-MIT-green.svg" alt="License"></a>
  </p>
</div>

---

## 🚀 Temel Özellikler

- ⚡ **Reaktif ve Asenkron**: RxJava3 ile tamamen asenkron, non-blocking operasyonlar.
- 🔒 **Tip-Güvenliği**: Güçlü tip kontrolü sayesinde daha az çalışma zamanı hatası.
- 🔄 **Otomatik Serileştirme**: Gson ile JSON ve Java nesneleri arasında otomatik dönüşüm.
- ⚙️ **Esnek Yapılandırma**: Timeout, yeniden deneme sayısı, rate limit ve loglama seviyesi gibi kritik ayarları kolayca yönetin.
- 📦 **Modern Kaynak Yönetimi**: AutoCloseable arayüzü ile try-with-resources bloğunda otomatik kaynak temizliği.

---

## 🛠️ Kurulum

### Gradle (Kotlin DSL)

```kotlin
repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}
dependencies {
    implementation 'com.github.nodestycom:java-api-client:main-SNAPSHOT'
}
```

---

## 🔑 Hızlı Başlangıç

### 1. API İstemcisini Yapılandırma

```java
import dev.astatic.nodestyclient.client.ClientOptions;
import dev.astatic.nodestyclient.client.NodestyApiClient;
import java.time.Duration;

String accessToken = System.getenv("NODESTY_API_TOKEN");

ClientOptions options = new ClientOptions(accessToken)
    .withTimeout(Duration.ofSeconds(45))
    .withRetry(5)
    .withLogLevel(ClientOptions.LogLevel.BODY);

try (NodestyApiClient client = new NodestyApiClient(options)) {
    client.getUser().getCurrentUser()
        .subscribe(
            user -> System.out.println("Merhaba, " + user.getUsername()),
            error -> System.err.println("Bir hata oluştu: " + error.getMessage())
        );

    Thread.sleep(5000);
} catch (InterruptedException e) {
    e.printStackTrace();
}
```

---

## 📖 API Referansı

### 👤 User Service (`client.getUser()`)

| Metot | Endpoint | Açıklama |
|-------|----------|----------|
| GET | `/users/@me` | Mevcut kullanıcı bilgilerini getirir |
| GET | `/services` | Kullanıcının tüm hizmetlerini listeler |
| GET | `/tickets` | Tüm destek biletlerini listeler |
| GET | `/tickets/{id}` | Belirli bileti getirir |
| GET | `/users/@me/invoices` | Faturaları listeler |
| GET | `/users/@me/invoices/{id}` | Fatura detaylarını getirir |
| GET | `/users/@me/sessions` | Aktif oturumları getirir |

### ☁️ VPS Service (`client.getVps()`)

| Metot | Endpoint | Açıklama |
|-------|----------|----------|
| GET | `/services/{id}/vps/info` | VPS detaylarını getirir |
| POST | `/services/{id}/vps/action` | VPS eylemi gönderir (REBOOT, SHUTDOWN) |
| GET | `/services/{id}/vps/graphs` | VPS kullanım grafiklerini alır |
| GET | `/services/{id}/vps/tasks` | VPS görevlerini listeler |
| POST | `/services/{id}/vps/password` | Root şifresini değiştirir |
| GET | `/services/{id}/vps/os-templates` | İşletim sistemi şablonlarını listeler |
| POST | `/services/{id}/vps/reinstall` | VPS'i yeniden kurar |
| POST | `/services/{id}/vps/backups/{date}/{file}` | Belirli yedeği geri yükler |

### 🔥 Firewall Service (`client.getFirewall()`)

| Metot | Endpoint | Açıklama |
|-------|----------|----------|
| GET | `/services/{id}/firewall/{ip}/rules` | Kuralları listeler |
| POST | `/services/{id}/firewall/{ip}/rules` | Yeni kural oluşturur |
| DELETE | `/services/{id}/firewall/{ip}/rules/port` | Porta göre siler |
| DELETE | `/services/{id}/firewall/{ip}/rules/app` | Uygulamaya göre siler |
| GET | `/services/{id}/firewall/{ip}/attack-logs` | Saldırı loglarını getirir |
| GET | `/services/{id}/firewall/{ip}/stats` | İstatistikleri getirir |
| GET | `/services/{id}/firewall/{ip}/reverse-dns` | Reverse DNS kayıtlarını listeler |
| PUT | `/services/{id}/firewall/{ip}/reverse-dns` | Reverse DNS günceller |
| GET | `/services/{id}/firewall/{ip}/attack-notification` | Bildirim ayarlarını getirir |
| PUT | `/services/{id}/firewall/{ip}/attack-notification` | Bildirim ayarlarını günceller |

### 🖥️ Dedicated Service (`client.getDedicated()`)

| Metot | Endpoint | Açıklama |
|-------|----------|----------|
| GET | `/services/{id}/dedicated/info` | Sunucu bilgilerini getirir |
| POST | `/services/{id}/dedicated/action` | Eylem gönderir |
| GET | `/services/{id}/dedicated/hardware` | Donanım bilgilerini getirir |
| GET | `/services/{id}/dedicated/tasks` | Görevleri listeler |
| GET | `/services/{id}/dedicated/os-templates` | OS şablonlarını getirir |
| POST | `/services/{id}/dedicated/reinstall` | Yeniden kurulum başlatır |
| GET | `/services/{id}/dedicated/reinstall-status` | Yeniden kurulum durumunu getirir |

---

## 🔐 Güvenlik ve En İyi Pratikler

- Token'ınızı kaynak kodda tutmayın, ortam değişkeni kullanın.
- `try-with-resources` ile istemciyi yönetin.
- Hata yönetimini her `.subscribe()` çağrısında sağlayın.
- `blockingGet()` gibi bloklayıcı metotlardan kaçının.

---

## 🤝 Katkıda Bulunma

1. Bu repoyu fork'layın
2. Yeni bir dal oluşturun: `git checkout -b feature/yeni-ozellik`
3. Değişikliklerinizi yapın ve commit'leyin
4. Dalınızı push'layın: `git push origin feature/yeni-ozellik`
5. Pull Request oluşturun

---

<div align="center">
  <p>⭐ Bu proje işinize yaradıysa bir yıldız bırakmayı unutmayın! ⭐</p>
  <p><strong>Nodesty Topluluğu için ❤️ ile geliştirildi.</strong></p>
</div>
