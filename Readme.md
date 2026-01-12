# SPECYFIKACJA WYMAGAŃ OPROGRAMOWANIA ECHOFORM (SRS)

## 1. Wstęp

### 1.1. Cel, Adresaci i Sposób Użycia

*Produkt i Wersja:*
Niniejszy dokument stanowi Specyfikację Wymagań Oprogramowania (SRS) dla systemu *EchoForm* - platformy do anonimowych ankiet z dynamicznymi pytaniami, w wersji 1.0 (MVP - Minimum Viable Product).

*Cel Dokumentu:*
Głównym zadaniem niniejszej specyfikacji jest szczegółowe określenie wymagań funkcjonalnych i niefunkcjonalnych aplikacji EchoForm, w tym jej funkcji, ograniczeń, założeń projektowych oraz atrybutów jakościowych. Dokument pełni rolę fundamentu do projektowania architektury, implementacji, testowania oraz oceny końcowej projektu w ramach przedmiotu Inżynieria Oprogramowania.

*Adresaci:*
Dokument jest przeznaczony dla:

- Członków zespołu projektowego, jako wytyczne do prac deweloperskich
- Prowadzącego zajęcia, jako podstawa do weryfikacji zakresu i złożoności logicznej projektu

*Sposób Użycia:*
Specyfikacja powinna być wykorzystywana jako:

- Punkt odniesienia przy implementacji złożonej logiki biznesowej systemu
- Kryterium weryfikacji poprawności funkcjonalności podczas fazy testów
- Podstawa do oceny zgodności systemu z założonymi celami biznesowymi i technicznymi

### 1.2. Wizja, Zakres i Cele Produktu

#### Wizja
EchoForm to platforma umożliwiająca tworzenie anonimowych ankiet z inteligentną logiką pytań, które realnie zmniejszają czas potrzebny na ich wypełnienie oraz zwiększają szczegółowość uzyskiwanych odpowiedzi. Dzięki dynamicznemu dostosowywaniu treści, system eliminuje zbędne pytania, co w połączeniu z całkowitym brakiem konieczności logowania i identyfikacji użytkowników, zapewnia pełną szczerość odpowiedzi oraz wyższą jakość danych niż w tradycyjnych, statycznych formularzach.

#### Zakres
System umożliwia:

- Tworzenie ankiet statycznych oraz dynamicznych - definiowanie warunkowego wyświetlania pytań. System automatycznie decyduje o ścieżce respondenta na podstawie jego wcześniejszych wyborów
- Bezlogowaniowy system dostępu - zarządzanie uprawnieniami do ankiety poprzez unikalne kody (tokeny), co gwarantuje poufność tożsamości
- Warunkowe wyświetlanie pytań - dynamiczne dostosowywanie formularza do odpowiedzi użytkownika
- Udostępnianie arkuszy w trzech trybach - publicznym (otwarty link), prywatnym (ograniczona lista odbiorców) oraz zabezpieczonym kodem (wymagane hasło lub token)
- Analizę wyników - średnie, rozkłady procentowe, najczęstsze odpowiedzi
- Automatyzację powiadomień - system informowania autora o postępach w zbieraniu wyników (powiadomienia e-mail)

#### Cele Biznesowe (KPI)

- Skuteczność wdrożenia MVP  
   Umożliwienie Instytucjom Edukacyjnym i Szkoleniowym osiągnięcie wzrostu wskaźnika ukończenia anonimowych ankiet o 25% w ciągu 12 miesięcy, w porównaniu ze standardowymi statycznymi ankietami (z wewnętrznych systemów instytucji)

- Skrócenie czasu wypełniania  
   Zmniejszenie średniego czasu potrzebnego na wypełnienie formularza o 30% dzięki zastosowaniu mechanizmu warunkowego wyświetlania pytań i eliminacji treści nieistotnych dla danego respondenta względem klasycznej statycznej ankiety

- Powtarzalność wyników  
   Wzrost wskaźnika ukończenia o założone 25% musi zostać odnotowany w co najmniej 70% wszystkich przeprowadzonych kampanii ankietowych, co potwierdzi stabilność rozwiązania niezależnie od grupy odbiorców

#### Poza Zakresem

- Konta respondentów - system nie przewiduje zakładania kont ani profili dla osób udzielających odpowiedzi. Dostęp odbywa się wyłącznie na podstawie tokenów lub linków, bez konieczności rejestracji
- Edycja wysłanych odpowiedzi - po ostatecznym zatwierdzeniu i wysłaniu ankiety przez respondenta, nie będzie możliwości powrotu do formularza w celu zmiany udzielonych odpowiedzi
- Komunikator w czasie rzeczywistym - system nie będzie posiadał modułu czatu ani forum do bezpośredniej komunikacji między autorem ankiety a respondentami
- Moduł egzaminacyjny i ocenianie - aplikacja nie służy do sprawdzania wiedzy. Brak funkcji punktowania odpowiedzi, limitów czasowych na pytanie oraz wystawiania ocen końcowych

### 1.3. Definicje i Skróty

| Termin | Znaczenie |
|--------|-----------|
| SRS | Specyfikacja Wymagań Oprogramowania |
| MVP | Minimalna wersja produktu (Minimum Viable Product) |
| KPI | Kluczowy wskaźnik efektywności (Key Performance Indicator) |
| Respondent | Osoba wypełniająca ankietę |
| Autor | Twórca ankiety |
| Dynamiczne pytania | Pytania zależne od wcześniejszych odpowiedzi |
| Token | Unikalny kod dostępu do ankiety |
| Warunkowe wyświetlanie | Mechanizm pokazywania pytań w zależności od poprzednich odpowiedzi |

## 2. Opis Ogólny

### 2.1. Główne Funkcje Produktu

- *Tworzenie ankiet* - intuicyjny kreator formularzy z obsługą różnych typów pytań (otwarte, zamknięte)
- *Dynamiczna logika pytań* - silnik sterujący wyświetlaniem pytań na podstawie wcześniejszych wyborów respondenta
- *Anonimowe wypełnianie* - proces zbierania odpowiedzi bez konieczności rejestracji i identyfikacji użytkownika
- *Kontrola dostępu* - zarządzanie uprawnieniami za pomocą unikalnych tokenów (kodów) oraz linków publicznych/prywatnych
- *Analiza wyników* - moduł generujący statystyki (średnie, rozkłady, najczęstsze odpowiedzi)
- *Powiadomienia e-mail* - automatyczne raporty przesyłane do autora ankiety

### 2.2. Klasy Użytkowników i Persony

#### Twórca Ankiety (Użytkownik Zalogowany)

*Rola:* Pracownik instytucji lub szkoleniowiec zarządzający procesem badawczym

*Uprawnienia:*
- Projektowanie ankiet
- Definiowanie logiki pytań
- Generowanie kodów dostępu
- Analiza zgromadzonych raportów i statystyk

*Wymagania:* Wymaga posiadania konta w systemie

---

#### Respondent (Użytkownik Anonimowy)

*Rola:* Student lub uczestnik szkolenia udzielający odpowiedzi

*Charakterystyka:* Osoba biorąca udział w badaniu, dla której priorytetem jest szybkość wypełnienia oraz gwarancja prywatności

*Uprawnienia:*
- Dostęp do treści ankiety
- Przesyłanie odpowiedzi

*Wymagania:* Nie posiada konta; dostęp uzyskuje wyłącznie poprzez unikalny token lub link

---

#### Persona 1 - Dr Andrzej Nowak (Twórca Ankiety)

| Aspekt | Opis |
|--------|------|
| *Rola* | Wykładowca akademicki |
| *Cel* | Szybkie zebranie danych i automatyczne otrzymanie wyników na e-mail |
| *Motywacja* | Chce analizować tylko istotne informacje |
| *Potrzeby* | • Logika warunkowa pytań (np. pomijanie nieistotnych sekcji)<br>• Automatyczne raporty<br>• Krótkie ankiety dla studentów |
| *Opis* | Dr Andrzej Nowak chce sprawnie zbierać opinie studentów. Kluczowe jest dla niego zastosowanie logiki warunkowej (np. „jeśli student nie był na wykładzie, pomiń pytania o materiały"), dzięki czemu ankieta zawiera tylko istotne pytania, a respondenci nie tracą czasu na zbędne pola. |

#### Persona 2 - Marta Kowalczyk (Szkoleniowiec)

| Aspekt | Opis |
|--------|------|
| *Rola* | Trenerka szkoleń |
| *Cel* | Ocena satysfakcji uczestników |
| *Motywacja* | Szybki dostęp do raportu |
| *Potrzeby* | • Brak logowania dla uczestników<br>• Automatyczne podsumowanie wyników<br>• Prosty interfejs |
| *Opis* | Marta organizuje szybkie badania satysfakcji po szkoleniach. Potrzebuje narzędzia, które nie wymaga logowania od uczestników, a jej samej pozwala błyskawicznie wygenerować raport końcowy bez ręcznego liczenia głosów. |

#### Persona 3 - Michał Lewandowski (Respondent - Student)

| Aspekt | Opis |
|--------|------|
| *Rola* | Student |
| *Cel* | Szybkie i anonimowe wypełnienie ankiety |
| *Motywacja* | Prywatność i oszczędność czasu |
| *Potrzeby* | • Brak logowania<br>• Krótka ankieta<br>• Pomijanie zbędnych pytań |
| *Opis* | Michał chce szybko i anonimowo ocenić zajęcia. Oczekuje, że wypełnianie ankiety potrwa tylko chwilę, ponieważ system pominie zbędne pytania, a brak konieczności logowania zapewni mu pełną swobodę. |

#### Persona 4 - Sandra Wiśniewska (Respondent - Uczestniczka Kursu)

| Aspekt | Opis |
|--------|------|
| *Rola* | Uczestniczka szkolenia |
| *Cel* | Udzielenie szczerych odpowiedzi |
| *Motywacja* | Pełna anonimowość |
| *Potrzeby* | • Gwarancja prywatności<br>• Brak konta<br>• Spersonalizowana ścieżka pytań |
| *Opis* | Sandra jest skłonna do szczerych odpowiedzi tylko wtedy, gdy ma 100% pewności, że nikt jej nie zidentyfikuje. Brak konieczności zakładania konta i krótka, spersonalizowana ścieżka pytań sprawiają, że nie porzuca ankiety w połowie. |

### 2.3. Ograniczenia Projektowe

#### 1. Ograniczenia Technologiczne

*Ograniczenie:* Brak logowania respondentów i brak identyfikatorów użytkowników

*Źródło:* Wymóg anonimowości, architektura systemu

*Wpływ na projekt:*
- Konieczność stosowania tokenów sesyjnych zamiast kont użytkowników
- Utrudnione wykrywanie duplikatów odpowiedzi
- Brak możliwości personalizacji ankiet
- Ograniczone mechanizmy kontroli dostępu

#### 2. Ograniczenia Biznesowe

*Ograniczenie:* Niski budżet utrzymania systemu (model zero-budget)

*Źródło:* Charakter edukacyjny projektu

*Wpływ na projekt:*
- Konieczność korzystania wyłącznie z darmowych usług hostingowych (np. Render, Oracle Cloud)
- Zastosowanie darmowych baz danych (np. MongoDB Atlas, Supabase)
- Wykorzystanie technologii open-source
- Prosta architektura warstwowa
- Brak drogich usług chmurowych
- Ograniczona skalowalność w porównaniu do rozwiązań komercyjnych

#### 3. Ograniczenia Dostępu (Stateless Access Control)

*Ograniczenie:* System musi obsługiwać dostęp do ankiet bez tworzenia kont i sesji dla respondentów

*Źródło:* Wymóg biznesowy dotyczący maksymalnego uproszczenia ścieżki respondenta

*Wpływ na architekturę:*
- Wyklucza użycie standardowych systemów zarządzania użytkownikami (np. Spring Security z bazą użytkowników) dla respondentów
- Logika sprawdzania uprawnień musi być zaimplementowana na poziomie zasobu (ankiety), a nie sesji zalogowanego użytkownika
- Obsługa dwóch trybów dostępu: Link Publiczny lub wspólne hasło do ankiety

#### 4. Ograniczenia Prawne

*Ograniczenie:* Zgodność z RODO i ochrona danych osobowych

*Źródło:* Prawo Unii Europejskiej

*Wpływ na projekt:*
- Brak przechowywania danych osobowych respondentów
- Przechowywanie danych wyłącznie na serwerach w UE
- Konieczność implementacji mechanizmów ochrony danych

### 2.4. Założenia Projektowe

#### Założenie 1 - Mechanizm dynamicznych pytań

*Założenie:* Mechanizm dynamicznych pytań pozostaje stabilny i poprawny nawet w złożonych ankietach.

*Ryzyko:* Błędna logika może pominąć kluczowe sekcje lub zadać nieprawidłowe pytania, co frustruje respondentów i obniża jakość danych.

*Plan walidacji:*
- *Co:* Testy poprawności logiki warunkowej.
- *Jak:* Ankiety testowe z różnymi scenariuszami odpowiedzi; weryfikacja prezentowanych pytań.
- *Kiedy:* Pierwszy sprint implementacyjny.
- *Kto:* Programiści i testerzy.

#### Założenie 2 - Mechanizm anonimowej sesji

*Założenie:* Mechanizm anonimowej sesji (token w przeglądarce lub sesja serwera) pozwala wznawiać ankietę po zamknięciu karty bez naruszania anonimowości.

*Ryzyko:* Niestabilna sesja (szybki timeout, utrata tokenu) powoduje utratę postępu i spadek wskaźnika ukończenia ankiet, co uderza w cel biznesowy.

*Plan walidacji:*
- *Co:* Poprawność wznawiania anonimowej ankiety.
- *Jak:* Testy manualne i automatyczne z zamykaniem/otwieraniem ankiety na różnych etapach.
- *Kiedy:* Na etapie implementacji MVP, przed testami użyteczności.
- *Kto:* Zespół deweloperski.