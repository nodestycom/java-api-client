# Nodesty Java API Client

[![Java](https://img.shields.io/badge/Java-21+-orange.svg)](https://www.oracle.com/java/)
[![API](https://img.shields.io/badge/Nodesty-API-green.svg)](https://nodesty.com)
[![Author](https://img.shields.io/badge/Author-Staticius-blue.svg)](https://github.com/staticius)

nodesty.com'un güçlü API'sine Java uygulamalarınızdan kolayca erişmenizi sağlayan modern, asenkron ve tip güvenli bir istemci kütüphanesi.

## 🚀 Özellikler

- **⚡ Asenkron Operasyonlar**: `CompletableFuture` ile tam asenkron destek
- **🔒 Tip Güvenliği**: Güçlü tip kontrolü ile Java records ve sınıfları
- **🔄 Otomatik Serileştirme**: JSON ↔ Java nesne dönüşümü otomatik
- **⚙️ Yapılandırılabilir**: Timeout, retry, rate limit ayarları
- **🛡️ Kapsamlı Hata Yönetimi**: Standart `ApiResponse<T>` yapısı
- **📦 Kaynaklar**: AutoCloseable ile otomatik kaynak yönetimi

## 📋 Desteklenen Servisler

| Servis | Açıklama | Erişim |
|--------|----------|--------|
| **User Service** | Kullanıcı profili, hizmetler, faturalar, destek biletleri | `apiClient.user()` |
| **VPS Service** | VPS yönetimi, yedekler, şifre değişimi, istatistikler | `apiClient.vps()` |
| **Dedicated Service** | Dedicated sunucu yönetimi, donanım bilgileri | `apiClient.dedicatedServer()` |
| **Firewall Service** | nShield kuralları, saldırı logları, rDNS yönetimi | `apiClient.firewall()` |

## 🛠️ Kurulum

```xml
    <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

    <dependency>
	    <groupId>com.github.nodestycom</groupId>
	    <artifactId>java-api-client</artifactId>
	    <version>1.0.0</version>
	</dependency>
```

```gradle.kts

repositories {
			mavenCentral()
			maven { url = uri("https://jitpack.io") }
		}

dependencies {
implementation("com.github.nodestycom:java-api-client:1.0.0")
	}
```

## 🔑 Başlangıç

### API Token Alma
1. [Nodesty kontrol paneli](https://nodesty.com/dashboard/my-account/access-tokens) adresine giriş yapın.

### Temel Kullanım

```java
import dev.astatic.nodestyclient.api.*;
import java.time.Duration;

// Token'ı ortam değişkeninden al (güvenlik için önerilir)
String accessToken = System.getenv("NODESTY_API_TOKEN");

// İstemci yapılandırması
RestClientOptions options = new RestClientOptions(accessToken)
    .withTimeout(Duration.ofSeconds(45))
    .withRetry(5)
    .withRateLimitOffset(100);

// İstemciyi başlat
try (NodestyApiClient apiClient = new NodestyApiClient(options)) {
    // API çağrılarınızı burada yapın
}
```

## 📖 Kullanım Örnekleri

### 👤 Kullanıcı Bilgileri

```java
// Mevcut kullanıcı bilgilerini al
apiClient.user().getCurrentUser()
    .thenAccept(response -> {
        if (response.isSuccess()) {
            CurrentUser user = response.getData();
            System.out.println("Merhaba " + user.fullName());
            System.out.println("Email: " + user.email());
        } else {
            System.err.println("Hata: " + response.getError());
        }
    })
    .exceptionally(ex -> {
        System.err.println("Beklenmeyen hata: " + ex.getMessage());
        return null;
    });
```

### 🖥️ VPS Yönetimi

```java
String vpsServiceId = "your-vps-service-id";

// VPS'i yeniden başlat
apiClient.vps().performAction(vpsServiceId, VpsAction.REBOOT)
    .thenAccept(response -> {
        if (response.isSuccess()) {
            System.out.println("VPS yeniden başlatıldı!");
        } else {
            System.err.println("Hata: " + response.getError());
        }
    });

// VPS yedeklerini listele
apiClient.vps().getBackups(vpsServiceId)
    .thenAccept(response -> {
        if (response.isSuccess()) {
            List<VpsBackup> backups = response.getData();
            backups.forEach(backup -> 
                System.out.println("Yedek: " + backup.date() + " - " + backup.file())
            );
        }
    });
```

### 🔧 Dedicated Sunucu

```java
String dedicatedServiceId = "your-dedicated-service-id";

// Donanım bilgilerini al
apiClient.dedicatedServer().getHardwareComponents(dedicatedServiceId)
    .thenAccept(response -> {
        if (response.isSuccess()) {
            List<DedicatedServerHardwareComponent> components = response.getData();
            System.out.println("Donanım Bileşenleri:");
            components.forEach(comp -> 
                System.out.println("  - " + comp.component() + ": " + 
                                 comp.model() + " (" + comp.value() + comp.valueSuffix() + ")")
            );
        }
    });
```

### 🛡️ Güvenlik Duvarı

```java
String serviceId = "your-service-id";
String ipAddress = "your-ip-address";

// Yeni güvenlik duvarı kuralı oluştur
FirewallCreateRuleData newRule = new FirewallCreateRuleData(25565, 123); // Minecraft Java Edition portu. (Bedrock için varsayılan 19132 dir.)
apiClient.firewall().createRule(serviceId, ipAddress, newRule)
    .thenAccept(response -> {
        if (response.isSuccess()) {
            System.out.println("Güvenlik duvarı kuralı oluşturuldu!");
        } else {
            System.err.println("Hata: " + response.getError());
        }
    });

// Saldırı loglarını görüntüle
apiClient.firewall().getAttackLogs(serviceId, ipAddress)
    .thenAccept(response -> {
        if (response.isSuccess()) {
            List<FirewallAttackLog> logs = response.getData();
            logs.forEach(log -> 
                System.out.println("Saldırı: " + log.timestamp() + " - " + log.attackType())
            );
        }
    });
```

## 🏗️ API Yanıt Yapısı

Tüm API çağrıları `ApiResponse<T>` döner:

```java
public class ApiResponse<T> {
    private boolean success;    // İşlem başarılı mı?
    private String error;       // Hata mesajı (varsa)
    private T data;            // Dönen veri
}

// Kullanım
response.isSuccess()  // boolean
response.getError()   // String
response.getData()    // T
```

## ⚙️ Yapılandırma Seçenekleri

```java
RestClientOptions options = new RestClientOptions(accessToken)
    .withTimeout(Duration.ofSeconds(30))     // İstek timeout (varsayılan: 30s)
    .withRetry(3)                           // Retry sayısı (varsayılan: 3)
    .withRateLimitOffset(50);               // Rate limit ofseti (varsayılan: 50ms)
```

## 📚 API Servisleri

### 👤 User Service (`apiClient.user()`)

| Metod | Açıklama | Endpoint |
|-------|----------|----------|
| `getCurrentUser()` | Kullanıcı profilini al | `GET /users/@me` |
| `getServices()` | Tüm hizmetleri listele | `GET /services` |
| `getTickets()` | Destek biletlerini listele | `GET /tickets` |
| `getTicket(id)` | Bilet detaylarını al | `GET /tickets/{id}` |
| `getInvoices()` | Faturaları listele | `GET /users/@me/invoices` |
| `getInvoice(id)` | Fatura detaylarını al | `GET /users/@me/invoices/{id}` |
| `getSessions()` | Aktif oturumları listele | `GET /users/@me/sessions` |

### 🖥️ VPS Service (`apiClient.vps()`)

| Metod | Açıklama | Endpoint |
|-------|----------|----------|
| `performAction(id, action)` | VPS eylemi gerçekleştir | `POST /services/{id}/vps/action` |
| `getBackups(id)` | Yedekleri listele | `GET /services/{id}/vps/backups` |
| `restoreBackup(id, date, file)` | Yedekten geri yükle | `POST /services/{id}/vps/backups/{date}/{file}` |
| `changePassword(id, data)` | Şifre değiştir | `POST /services/{id}/vps/change-password` |
| `getGraphs(id)` | İstatistik grafiklerini al | `GET /services/{id}/vps/graphs` |
| `getDetails(id)` | VPS detaylarını al | `GET /services/{id}/vps/info` |
| `getOsTemplates(id)` | OS şablonlarını listele | `GET /services/{id}/vps/os-templates` |
| `reinstall(id, data)` | VPS'i yeniden kur | `POST /services/{id}/vps/reinstall` |
| `getTasks(id)` | Görevleri listele | `GET /services/{id}/vps/tasks` |

### 🔧 Dedicated Service (`apiClient.dedicatedServer()`)

| Metod | Açıklama | Endpoint |
|-------|----------|----------|
| `performAction(id, action)` | Sunucu eylemi gerçekleştir | `POST /services/{id}/dedicated/action` |
| `getHardwareComponents(id)` | Donanım bilgilerini al | `GET /services/{id}/dedicated/hardware` |
| `getDetails(id)` | Sunucu detaylarını al | `GET /services/{id}/dedicated/info` |
| `getOsTemplates(id)` | OS şablonlarını listele | `GET /services/{id}/dedicated/os-templates` |
| `getReinstallStatus(id)` | Yeniden kurulum durumu | `GET /services/{id}/dedicated/reinstall-status` |
| `reinstall(id, data)` | Sunucuyu yeniden kur | `POST /services/{id}/dedicated/reinstall` |
| `getTasks(id)` | Görevleri listele | `GET /services/{id}/dedicated/tasks` |

### 🛡️ Firewall Service (`apiClient.firewall()`)

| Metod | Açıklama | Endpoint |
|-------|----------|----------|
| `getAttackLogs(serviceId, ip)` | Saldırı loglarını al | `GET /services/{id}/firewall/{ip}/attack-logs` |
| `getAttackNotificationSettings(serviceId, ip)` | Bildirim ayarlarını al | `GET /services/{id}/firewall/{ip}/attack-notification` |
| `updateAttackNotificationSettings(serviceId, ip, data)` | Bildirim ayarlarını güncelle | `PUT /services/{id}/firewall/{ip}/attack-notification` |
| `deleteReverseDns(serviceId, ip)` | rDNS'i sıfırla | `DELETE /services/{id}/firewall/{ip}/rdns` |
| `getReverseDns(serviceId, ip)` | rDNS'i al | `GET /services/{id}/firewall/{ip}/rdns` |
| `upsertReverseDns(serviceId, ip, rdns)` | rDNS'i ayarla | `PUT /services/{id}/firewall/{ip}/rdns` |
| `deleteRule(serviceId, ip, ruleId)` | Kuralı sil | `DELETE /services/{id}/firewall/{ip}/rules/{ruleId}` |
| `getRules(serviceId, ip)` | Kuralları listele | `GET /services/{id}/firewall/{ip}/rules` |
| `createRule(serviceId, ip, data)` | Yeni kural oluştur | `POST /services/{id}/firewall/{ip}/rules` |
| `getStatistics(serviceId, ip)` | İstatistikleri al | `GET /services/{id}/firewall/{ip}/stats` |

## 🔐 Güvenlik En İyi Uygulamaları

### ✅ Yapılması Gerekenler
- API token'ı ortam değişkenlerinde saklayın
- `try-with-resources` kullanarak kaynak yönetimi yapın
- Her zaman `response.isSuccess()` kontrolü yapın
- `exceptionally()` ile hata yakalayın

### ❌ Yapılmaması Gerekenler
- Token'ı kaynak koduna gömmeyİn
- `join()` metodunu UI thread'lerinde kullanmayın
- API yanıtlarını kontrol etmeden kullanmayın

## 🚀 Performans İpuçları

- **Asenkron Kullanım**: `join()` yerine `thenAccept()`, `thenApply()` kullanın
- **Batch İşlemler**: Mümkünse birden fazla işlemi paralel olarak yapın
- **Timeout Ayarları**: Ağ koşullarınıza göre timeout değerlerini ayarlayın
- **Rate Limiting**: API limits'lerine dikkat edin

## 🧪 Test Örneği

```java
@Test
public void testUserService() {
    String testToken = System.getenv("TEST_NODESTY_TOKEN");
    RestClientOptions options = new RestClientOptions(testToken);
    
    try (NodestyApiClient client = new NodestyApiClient(options)) {
        ApiResponse<CurrentUser> response = client.user()
            .getCurrentUser()
            .join();
            
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertNotNull(response.getData().email());
    }
}
```

## 🐛 Sorun Giderme

### Yaygın Hatalar

**401 Unauthorized**
- API token'ınızı kontrol edin
- Token'ın aktif olduğundan emin olun

**Timeout Hataları**
- Timeout değerini artırın
- Ağ bağlantınızı kontrol edin

**Rate Limiting**
- Rate limit offset değerini artırın
- İstekler arasında daha fazla bekleyin

## 📝 Changelog

### v1.0.0
- İlk stabil sürüm
- Tüm Nodesty API endpoint'leri destekleniyor
- Asenkron operasyonlar
- Kapsamlı hata yönetimi

## 🤝 Katkıda Bulunma

1. Projeyi forklayın (`git clone`)
2. Feature branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Değişikliklerinizi commit edin (`git commit -m 'Add amazing feature'`)
4. Branch'inizi push edin (`git push origin feature/amazing-feature`)
5. Pull Request oluşturun

## 🔗 Bağlantılar

- [Nodesty Website](https://nodesty.com)
- [API Dökümantasyonu](https://nodesty.com/docs)
## ⭐ Destek

Bu proje faydalı bulduysanız, lütfen ⭐ vererek destekleyin!

---

**Made with ❤️ for Nodesty Community.
