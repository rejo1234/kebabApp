 **Opis projektu**  
 Aplikacja backendowa napisana w Spring Boot służy do zarządzania zamówieniami (Order) w kontekście relacji pomiędzy użytkownikami (User),
 restauracjami (Restaurant), produktami (Product) i repozytoriami zamówień (Repository). Aplikacja odwzorowuje realistyczny przepływ składania i
 zarządzania zamówieniami w środowisku wielopodmiotowym (wielu użytkowników, restauracji, produktów).

 **Bezpieczeństwo: JWT i filtry**  
 Aplikacja wykorzystuje JWT (JSON Web Token) do uwierzytelniania i autoryzacji użytkowników. System:

- Generuje token JWT po zalogowaniu się użytkownika,  
- Wykorzystuje własne filtry Spring Security do przechwytywania i weryfikacji żądań HTTP,  
- Chroni endpointy – dostęp mają tylko użytkownicy z poprawnym tokenem,  
- Przechwytuje żądania przez własny filtr JWT i przypisuje kontekst użytkownika do Spring Security,  
- Weryfikuje dane wejściowe (np. przy rejestracji, logowaniu) z użyciem walidacji – zwiększając bezpieczeństwo systemu.

**Struktura danych (encje)**  
User – reprezentuje użytkownika systemu; może tworzyć zamówienia i być powiązany z wieloma restauracjami i repozytoriami.  
Relacje: @ManyToMany z Restaurant, Repository; @OneToMany z Order.

Restaurant – reprezentuje restaurację z danymi adresowymi; może mieć wielu użytkowników, repozytoria i zamówienia.  
Relacje: @ManyToMany z User, Repository; @OneToMany z Order.

Repository – magazyn z produktami; współdzielony między użytkownikami i restauracjami.  
Relacje: @ManyToMany z User, Restaurant; @OneToMany z Product, Order.

Product – produkt przypisany do repozytorium.  
Relacja: @ManyToOne do Repository.

Order – zamówienie z listą produktów (jako OrderProductDto), przypisane do użytkownika, restauracji i repozytorium.  
Relacje: @ManyToOne do User, Restaurant, Repository; @ElementCollection dla listy produktów.

# **Funkcjonalność**  
- Rejestracja użytkownika: Nowy użytkownik może się zarejestrować podając dane (np. e-mail, hasło),  
- Po zalogowaniu otrzymuje token JWT, który musi dołączać do kolejnych żądań.  
- Każda główna encja (User, Restaurant, Repository, Product, Order) obsługuje wiele takich samych endpointów: create, modify, delete oraz get (pobranie danych)  
- Tworzenie zamówienia: Użytkownik podaje nazwę produktu z wagą, komentarz i termin odbioru itp. Zamówienie zostaje powiązane z użytkownikiem, restauracją i magazynem.  
- Inicjalizacja danych: Przy starcie aplikacji uruchamiany jest `CommandLineRunner`, który może tworzyć przykładowe dane (`User`, `Repository`, `Product` itd.)  
- System wspiera separację danych między użytkownikami i kontekstową widoczność (np. użytkownik widzi tylko swoje zamówienia lub produkty z przypisanego repozytorium).
