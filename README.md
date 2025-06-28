# Nodesty Java API İstemcisi

Bu depo, Nodesty API ile kolayca etkileşim kurmanızı sağlayan hafif ve asenkron bir Java istemci kütüphanesini içerir.
Kullanıcı bilgileri, VPS yönetimi, güvenlik duvarı ayarları ve dedicated sunucu detayları gibi çeşitli Nodesty
hizmetlerine erişim sağlar.

## İçindekiler

- [Özellikler](#özellikler)
- [Gereksinimler](#gereksinimler)
- [Kurulum](#kurulum)
- [Kullanım](#kullanım)
    - [API İstemcisini Başlatma](#api-istemcisini-başlatma)
    - [Kullanıcı Servisi](#kullanıcı-servisi)
    - [VPS Servisi](#vps-servisi)
    - [Güvenlik Duvarı Servisi](#güvenlik-duvarı-servisi)
    - [Dedicated Sunucu Servisi](#dedicated-sunucu-servisi)
- [Yanıt İşleme ve Hata Yönetimi](#yanıt-işleme-ve-hata-yönetimi)
- [API Modelleri](#api-modelleri)
- [Katkıda Bulunma](#katkıda-bulunma)
- [Lisans](#lisans)

## Özellikler

- **Asenkron API Çağrıları**: `java.util.concurrent.CompletableFuture` kullanarak bloklamayan operasyonlar sağlar,
  uygulamanızın performansını artırır.
- **Kapsamlı Modeller**: Nodesty API'den gelen verileri temsil eden, record sınıfları aracılığıyla iyi tanımlanmış,
  değişmez (immutable) Java modelleri içerir.
- **Modüler Servisler**: Her bir API alanı (Örn: User, VPS, Firewall, Dedicated Server) için ayrı, düzenli ve
  yönetilebilir servis sınıfları bulunur.
- **Özelleştirilebilir Seçenekler**: İstek zaman aşımı (Duration ile), yeniden deneme mekanizmaları ve diğer ağ ayarları
  gibi istemci davranışlarını RestClientOptions sınıfı ile kolayca yapılandırma yeteneği sunar.
- **Otomatik JSON Serileştirme/Deserileştirme**: Popüler Gson kütüphanesi entegrasyonu sayesinde API istek gövdelerinin
  ve yanıtlarının otomatik olarak JSON'dan Java objelerine ve tersine dönüşümünü sağlar.
- **Güvenli Kimlik Doğrulama**: Nodesty Kişisel Erişim Jetonu (PAT) kullanarak güvenli ve standart bir kimlik doğrulama
  yöntemi uygular.

## Gereksinimler

- Java Development Kit (JDK) 17 veya üzeri (Java 17'nin record sınıflarını ve Duration tipinin gelişmiş kullanımını
  gerektirir).
- Maven veya Gradle (proje bağımlılıklarını yönetmek için).

## Kurulum

Projenize bağımlılıkları eklemek için kullandığınız build aracına göre aşağıdaki adımları izleyin.

https://jitpack.io/#staticius/NodestyAPI/v1.0.1

## Kullanım

İstemciyi kullanmaya başlamak için geçerli bir Nodesty Personal Access Token'a (PAT) ihtiyacınız olacaktır. Bu token'ı
Nodesty kontrol panelinizdeki API ayarları bölümünden oluşturabilir veya mevcut birini kullanabilirsiniz.

Aşağıdaki örnekler, ana uygulama sınıfınızda (Main.java gibi) temel kullanım senaryolarını adım adım göstermektedir.

### Önerilen Paket Yapısı

```
src/main/java/
└── dev/
    └── astatic/
        └── nodestyclient/
            ├── api/              <-- Temel istemci sınıfları ve API modelleri
            │   ├── NodestyApiClient.java
            │   ├── ApiFetchFunction.java
            │   ├── ApiResponse.java
            │   └── RestClientOptions.java
            ├── model/            <-- Tüm API'den dönen veri modelleri (User, VpsDetails vb.)
            │   ├── User.java
            │   ├── VpsDetails.java
            │   └── ... (Diğer tüm veri modeli sınıfları)
            └── service/          <-- Her bir API alanı için özel servis sınıfları
                ├── UserApiService.java
                ├── VpsApiService.java
                ├── FirewallApiService.java
                └── DedicatedApiService.java
```

### API İstemcisini Başlatma

`NodestyApiClient` sınıfını başlatırken, Nodesty PAT'nizi zorunlu olarak belirtmeniz gerekir. Ayrıca, isteğe bağlı
olarak istek zaman aşımı, yeniden deneme sayısı gibi ek seçenekleri `RestClientOptions` objesi ile
yapılandırabilirsiniz.

```java
import dev.astatic.nodestyclient.api.NodestyApiClient;
import dev.astatic.nodestyclient.api.RestClientOptions;

import java.io.IOException;
import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        // !!! BURAYI KENDİ GERÇEK NODESTY PAT TOKEN'INIZLA DEĞİŞTİRİN !!!
        final String YOUR_ACCESS_TOKEN = "YOUR_NODESTY_PAT_TOKEN_HERE";

        if ("YOUR_NODESTY_PAT_TOKEN_HERE".equals(YOUR_ACCESS_TOKEN) || YOUR_ACCESS_TOKEN.isEmpty()) {
            System.err.println("Hata: Lütfen geçerli bir Nodesty PAT (Personal Access Token) sağlayın.");
            System.exit(1); // Uygulamayı sonlandır
        }

        // İstemci seçeneklerini yapılandırın
        // .withRetry(sayı): Başarısız isteklerde kaç kez daha deneneceğini ayarlar.
        // .withTimeout(Duration): İsteklerin zaman aşımı süresini ayarlar.
        RestClientOptions options = new RestClientOptions(YOUR_ACCESS_TOKEN)
                .withRetry(2) // Bir isteğin başarısız olması durumunda 2 kez daha dene (toplam 3 deneme)
                .withTimeout(Duration.ofSeconds(60)); // İstek zaman aşımı 60 saniye

        // Nodesty API istemcisini oluştur
        NodestyApiClient client = new NodestyApiClient(options);

        // --- API çağrıları burada yapılacak ---
        // Örnek bir çağrı:
        fetchAndDisplayCurrentUser(client);

        // Uygulama kapanırken veya istemciye artık ihtiyaç duyulmadığında
        // HTTP bağlantılarını temizlemek için istemciyi kapatmayı unutmayın.
        try {
            client.close();
            System.out.println("NodestyApiClient başarıyla kapatıldı.");
        } catch (IOException e) {
            System.err.println("NodestyApiClient kapatılırken hata oluştu: " + e.getMessage());
        }
    }

    // Örnek bir metod
    private static void fetchAndDisplayCurrentUser(NodestyApiClient client) {
        client.user().getCurrentUser().thenAccept(response -> {
            if (response.isSuccess()) {
                System.out.println("Kullanıcı Bilgileri:");
                System.out.println("  Ad: " + response.getData().firstName());
                System.out.println("  Soyad: " + response.getData().lastName());
                System.out.println("  E-posta: " + response.getData().email());
                // Diğer kullanıcı bilgileri...
            } else {
                System.err.println("Mevcut kullanıcı bilgileri alınamadı: " + response.getError());
            }
        }).exceptionally(ex -> {
            System.err.println("getCurrentUser çağrısı sırasında beklenmeyen bir hata oluştu: " + ex.getMessage());
            return null;
        }).join(); // Asenkron çağrının tamamlanmasını bekler
    }
}
```

### Kullanıcı Servisi

Kullanıcıyla ilgili işlemleri (`UserApiService` üzerinden) gerçekleştirebilirsiniz. Buna mevcut kullanıcının profil
bilgilerini almak, faturaları listelemek veya belirli bir faturayı sorgulamak dahildir.

```java
import dev.astatic.nodestyclient.api.models.User;
import dev.astatic.nodestyclient.api.models.Invoice;
import java.util.List;

// Mevcut kullanıcı bilgilerini al
client.user().getCurrentUser().thenAccept(response -> {
    if (response.isSuccess()) {
        User currentUser = response.getData();
        System.out.println("Mevcut Kullanıcı: " + currentUser.firstName() + " " + currentUser.lastName());
        System.out.println("Email: " + currentUser.email());
    } else {
        System.err.println("Mevcut kullanıcı alınamadı: " + response.getError());
    }
}).exceptionally(ex -> {
    System.err.println("getCurrentUser çağrısı sırasında hata: " + ex.getMessage());
    return null;
}).join();

// Tüm faturaları al
client.user().getInvoices().thenAccept(response -> {
    if (response.isSuccess()) {
        List<Invoice> invoices = response.getData();
        if (invoices != null && !invoices.isEmpty()) {
            System.out.println("İlk Fatura ID: " + invoices.get(0).id());
            System.out.println("Toplam Fatura Sayısı: " + invoices.size());
        } else {
            System.out.println("Fatura bulunamadı.");
        }
    } else {
        System.err.println("Faturalar alınamadı: " + response.getError());
    }
}).exceptionally(ex -> {
    System.err.println("getInvoices çağrısı sırasında hata: " + ex.getMessage());
    return null;
}).join();

// Belirli bir faturayı ID ile al
String invoiceId = "inv_example123"; // Örnek fatura ID
client.user().getInvoiceById(invoiceId).thenAccept(response -> {
    if (response.isSuccess()) {
        Invoice invoice = response.getData();
        if (invoice != null) {
            System.out.println("Fatura Detayları (ID: " + invoice.id() + "): " + invoice.total() + " " + invoice.currency());
        }
    } else {
        System.err.println("Fatura " + invoiceId + " alınamadı: " + response.getError());
    }
}).exceptionally(ex -> {
    System.err.println("getInvoiceById çağrısı sırasında hata: " + ex.getMessage());
    return null;
}).join();
```

### VPS Servisi

Sanal Sunucularla (VPS) ilgili işlemleri (`VpsApiService` üzerinden) gerçekleştirebilirsiniz. Buna VPS detaylarını
almak, eylemler gerçekleştirmek (yeniden başlatma, başlatma, kapatma), yedekleri yönetmek veya işletim sistemi yeniden
kurulumu yapmak dahildir.

```java
import dev.astatic.nodestyclient.api.models.VpsDetails;
import dev.astatic.nodestyclient.api.models.VpsAction;
import dev.astatic.nodestyclient.api.models.VpsBackup;
import dev.astatic.nodestyclient.api.models.VpsReinstallData;
import java.util.List;

String vpsServiceId = "srv_your_vps_id_here"; // Kendi gerçek VPS hizmet ID'niz

// VPS detaylarını al
client.vps().getDetails(vpsServiceId).thenAccept(response -> {
    if (response.isSuccess()) {
        VpsDetails vps = response.getData();
        if (vps != null) {
            System.out.println("VPS Host Adı: " + vps.hostname());
            System.out.println("VPS Durumu: " + vps.status());
            System.out.println("IP Adresi: " + vps.ip());
        }
    } else {
        System.err.println("VPS detayları alınamadı: " + response.getError());
    }
}).exceptionally(ex -> {
    System.err.println("getDetails çağrısı sırasında hata: " + ex.getMessage());
    return null;
}).join();

// VPS'i yeniden başlat
client.vps().performAction(vpsServiceId, VpsAction.REBOOT).thenAccept(response -> {
    if (response.isSuccess()) {
        System.out.println("VPS yeniden başlatma isteği başarıyla gönderildi.");
    } else {
        System.err.println("VPS yeniden başlatılamadı: " + response.getError());
    }
}).exceptionally(ex -> {
    System.err.println("performAction çağrısı sırasında hata: " + ex.getMessage());
    return null;
}).join();

// VPS yedeklerini al
client.vps().getBackups(vpsServiceId).thenAccept(response -> {
    if (response.isSuccess()) {
        List<VpsBackup> backups = response.getData();
        if (backups != null && !backups.isEmpty()) {
            System.out.println("İlk Yedek Tarihi: " + backups.get(0).createdAt());
            System.out.println("Toplam Yedek Sayısı: " + backups.size());
        } else {
            System.out.println("VPS yedeği bulunamadı.");
        }
    } else {
        System.err.println("VPS yedekleri alınamadı: " + response.getError());
    }
}).exceptionally(ex -> {
    System.err.println("getBackups çağrısı sırasında hata: " + ex.getMessage());
    return null;
}).join();

// VPS işletim sistemi yeniden kurulumu (DİKKAT: Mevcut verileri silecektir!)
// Lütfen bu işlemi dikkatli yapın ve osId için geçerli bir ID kullandığınızdan emin olun.
/*
VpsReinstallData reinstallData = new VpsReinstallData("YourStrongRootPassword", 123); // osId'yi değiştirin
client.vps().reinstall(vpsServiceId, reinstallData).thenAccept(response -> {
    if (response.isSuccess()) {
        System.out.println("VPS yeniden kurulum isteği başarıyla gönderildi.");
    } else {
        System.err.println("VPS yeniden kurulamadı: " + response.getError());
    }
}).exceptionally(ex -> {
    System.err.println("reinstall çağrısı sırasında hata: " + ex.getMessage());
    return null;
}).join();
*/
```

### Güvenlik Duvarı Servisi

Shield güvenlik duvarı ile ilgili işlemleri (`FirewallApiService` üzerinden) gerçekleştirebilirsiniz. Buna saldırı
günlüklerini almak, IP adreslerini engellemek/beyaz listeye almak, ters DNS (rDNS) ayarlarını yönetmek ve güvenlik
duvarı kurallarını yapılandırmak dahildir.

```java
import dev.astatic.nodestyclient.api.models.FirewallAttackLog;
import dev.astatic.nodestyclient.api.models.FirewallBlockAction;
import dev.astatic.nodestyclient.api.models.FirewallReverseDns;
import dev.astatic.nodestyclient.api.models.FirewallRule;
import java.util.List;

String firewallServiceId = "srv_your_firewall_id_here"; // Kendi gerçek Güvenlik Duvarı hizmet ID'niz
String ipAddress = "192.168.1.100"; // Güvenlik duvarı ile ilişkili bir IP adresi

// Saldırı günlüklerini al
client.firewall().getAttackLogs(firewallServiceId, ipAddress).thenAccept(response -> {
    if (response.isSuccess()) {
        List<FirewallAttackLog> logs = response.getData();
        if (logs != null && !logs.isEmpty()) {
            System.out.println("İlk Saldırı Günlüğü Tipi: " + logs.get(0).type());
            System.out.println("Toplam Saldırı Günlüğü Sayısı: " + logs.size());
        } else {
            System.out.println("Saldırı günlüğü bulunamadı.");
        }
    } else {
        System.err.println("Saldırı günlükleri alınamadı: " + response.getError());
    }
}).exceptionally(ex -> {
    System.err.println("getAttackLogs çağrısı sırasında hata: " + ex.getMessage());
    return null;
}).join();

// Bir IP'yi engelle
String ipToBlock = "1.2.3.4";
client.firewall().blockIp(firewallServiceId, ipToBlock, FirewallBlockAction.BLOCK).thenAccept(response -> {
    if (response.isSuccess()) {
        System.out.println("IP " + ipToBlock + " başarıyla engellendi.");
    } else {
        System.err.println("IP " + ipToBlock + " engellenemedi: " + response.getError());
    }
}).exceptionally(ex -> {
    System.err.println("blockIp çağrısı sırasında hata: " + ex.getMessage());
    return null;
}).join();

// Mevcut ters DNS (rDNS) ayarlarını al
client.firewall().getReverseDns(firewallServiceId, ipAddress).thenAccept(response -> {
    if (response.isSuccess()) {
        FirewallReverseDns rdns = response.getData();
        if (rdns != null) {
            System.out.println("Mevcut rDNS: " + rdns.rdns());
        }
    } else {
        System.err.println("rDNS bilgisi alınamadı: " + response.getError());
    }
}).exceptionally(ex -> {
    System.err.println("getReverseDns çağrısı sırasında hata: " + ex.getMessage());
    return null;
}).join();
```

### Dedicated Sunucu Servisi

Dedicated sunucularla ilgili işlemleri (`DedicatedApiService` üzerinden) gerçekleştirebilirsiniz. Buna sunucu
detaylarını almak, donanım bilgilerini sorgulamak, eylemler gerçekleştirmek (yeniden başlatma, kapatma), işletim sistemi
yeniden kurulumu yapmak ve bant genişliği kullanımını izlemek dahildir.

```java
import dev.astatic.nodestyclient.api.models.DedicatedServerDetails;
import dev.astatic.nodestyclient.api.models.DedicatedServerAction;
import dev.astatic.nodestyclient.api.models.DedicatedServerHardwareComponent;
import dev.astatic.nodestyclient.api.models.DedicatedServerBandwidthUsage;
import java.util.List;

String dedicatedServiceId = "srv_your_dedicated_id_here"; // Kendi gerçek Dedicated Sunucu hizmet ID'niz

// Dedicated sunucu detaylarını al
client.dedicatedServer().getDetails(dedicatedServiceId).thenAccept(response -> {
    if (response.isSuccess()) {
        DedicatedServerDetails dedicated = response.getData();
        if (dedicated != null) {
            System.out.println("Dedicated Sunucu Host Adı: " + dedicated.hostname());
            System.out.println("Dedicated Sunucu Durumu: " + dedicated.status());
            System.out.println("İşletim Sistemi: " + dedicated.os());
        }
    } else {
        System.err.println("Dedicated sunucu detayları alınamadı: " + response.getError());
    }
}).exceptionally(ex -> {
    System.err.println("getDetails çağrısı sırasında hata: " + ex.getMessage());
    return null;
}).join();

// Dedicated sunucuyu kapat
client.dedicatedServer().performAction(dedicatedServiceId, DedicatedServerAction.SHUTDOWN).thenAccept(response -> {
    if (response.isSuccess()) {
        System.out.println("Dedicated sunucu kapatma isteği başarıyla gönderildi.");
    } else {
        System.err.println("Dedicated sunucu kapatılamadı: " + response.getError());
    }
}).exceptionally(ex -> {
    System.err.println("performAction çağrısı sırasında hata: " + ex.getMessage());
    return null;
}).join();

// Donanım bileşenlerini al
client.dedicatedServer().getHardwareComponents(dedicatedServiceId).thenAccept(response -> {
    if (response.isSuccess()) {
        List<DedicatedServerHardwareComponent> components = response.getData();
        if (components != null && !components.isEmpty()) {
            System.out.println("CPU Modeli: " + components.stream()
                .filter(c -> c.component().equals("CPU"))
                .findFirst()
                .map(DedicatedServerHardwareComponent::model)
                .orElse("N/A"));
        } else {
            System.out.println("Donanım bileşeni bulunamadı.");
        }
    } else {
        System.err.println("Donanım bileşenleri alınamadı: " + response.getError());
    }
}).exceptionally(ex -> {
    System.err.println("getHardwareComponents çağrısı sırasında hata: " + ex.getMessage());
    return null;
}).join();

// Bant genişliği kullanımını al
client.dedicatedServer().getBandwidthUsage(dedicatedServiceId).thenAccept(response -> {
    if (response.isSuccess()) {
        List<DedicatedServerBandwidthUsage> usages = response.getData();
        if (usages != null && !usages.isEmpty()) {
            DedicatedServerBandwidthUsage lastUsage = usages.get(usages.size() - 1);
            System.out.println("Son Bant Genişliği Kullanımı (in/out): " + 
                lastUsage.in() + "GB / " + lastUsage.out() + "GB");
        } else {
            System.out.println("Bant genişliği kullanımı verisi bulunamadı.");
        }
    } else {
        System.err.println("Bant genişliği kullanımı alınamadı: " + response.getError());
    }
}).exceptionally(ex -> {
    System.err.println("getBandwidthUsage çağrısı sırasında hata: " + ex.getMessage());
    return null;
}).join();
```

## Yanıt İşleme ve Hata Yönetimi

Tüm API çağrıları, `CompletableFuture<ApiResponse<T>>` tipinde bir nesne döndürür. Bu, asenkron işlemleri yönetmek için
Java'nın standart yaklaşımıdır.

### Başarılı Yanıtlar

- `.thenAccept()` metodunu kullanarak API isteği başarılı olduğunda (HTTP 2xx durum kodu) geri dönen veriyi
  işleyebilirsiniz.
- `ApiResponse.isSuccess()` metodu `true` dönecektir ve `ApiResponse.getData()` ile veriye erişebilirsiniz.

### API Hataları

- API'nin bir hata kodu (örneğin HTTP 4xx veya 5xx) ile yanıt vermesi durumunda, `ApiResponse.isSuccess()` metodu
  `false` dönecektir.
- `ApiResponse.getError()` ile API tarafından sağlanan hata mesajına erişebilirsiniz.

### Beklenmeyen Hatalar

- Ağ bağlantısı sorunları, JSON ayrıştırma hataları veya diğer çalışma zamanı istisnaları gibi beklenmedik durumlar
  `.exceptionally()` bloğu ile yakalanabilir.

```java
import java.util.concurrent.CompletableFuture;
import dev.astatic.nodestyclient.api.ApiResponse;

// Örnek bir API çağrısı
CompletableFuture<ApiResponse<YourDataType>> someApiClientCall = client.someService().someMethod();

someApiClientCall
    .thenAccept(response -> {
        if (response.isSuccess()) {
            // API işlemi başarılı oldu, veriye erişin
            System.out.println("Veri alındı: " + response.getData());
        } else {
            // API, hata mesajı ile yanıt verdi
            System.err.println("API Hatası: " + response.getError());
        }
    })
    .exceptionally(ex -> {
        // İstek sırasında beklenmeyen bir hata oluştu
        System.err.println("Beklenmeyen bir hata oluştu: " + ex.getMessage());
        return null;
    })
    .join(); // main metodunda ise asenkron çağrının tamamlanmasını bekler
```

## API Modelleri

Bu kütüphanede, Nodesty API'den dönen tüm JSON yanıtlarını ve API'ye gönderilen istek gövdelerini temsil etmek için Java
record sınıfları ve enum'lar kullanılır. Tüm bu model sınıfları `dev.astatic.nodestyclient.model` paketi altında yer
alır.

### Genel Özellikler

- **Immutable (Değişmez)**: record sınıfları, oluşturulduktan sonra durumları değiştirilemeyen immutable nesnelerdir.
- **Gson Uyumluluğu**: Tüm modeller, Gson kütüphanesi ile sorunsuz bir şekilde JSON'a serileştirilir ve JSON'dan
  deserileştirilir.
- **Kapsamlılık**: Her bir API yanıt yapısına veya istek gövdesine karşılık gelen ayrı bir record sınıfı veya enum
  bulunur.

### Örnek Model Kategorileri

- **Kullanıcı Modelleri**: User, Service, Ticket, Invoice, Session, vb.
- **VPS Modelleri**: VpsDetails, VpsAction, VpsBackup, VpsOsTemplate, VpsTask, vb.
- **Güvenlik Duvarı Modelleri**: FirewallAttackLog, FirewallRule, AttackNotificationSettings, FirewallBlockAction, vb.
- **Dedicated Sunucu Modelleri**: DedicatedServerDetails, DedicatedServerAction, DedicatedServerHardwareComponent,
  DedicatedServerReinstallStatus, vb.

## Katkıda Bulunma

Projenin gelişimine katkıda bulunmak isteyen herkese kapımız açık! Eğer bu kütüphaneyi geliştirmek, hata düzeltmeleri
yapmak veya yeni özellikler eklemek isterseniz, lütfen aşağıdaki adımları izleyerek bir Pull Request (PR) açmaktan
çekinmeyin:

1. Bu depoyu (repository) kendi GitHub hesabınıza çatallayın (fork).
2. Yeni bir özellik dalı oluşturun (`git checkout -b feature/AmazingNewFeature`).
3. Değişikliklerinizi yapın ve anlamlı commit mesajlarıyla kaydedin
