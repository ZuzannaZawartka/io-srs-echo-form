# SPECYFIKACJA WYMAGAŃ OPROGRAMOWANIA ECHOFORM (SRS)

## 1. Wstęp

   ### 1.1. Cel, Adresaci i Sposób Użycia

   Produkt i Wersja:
   Niniejszy dokument stanowi Specyfikację Wymagań Oprogramowania (SRS) dla systemu EchoForm - platformy do anonimowych ankiet z dynamicznymi pytaniami, w wersji 1.0 (MVP - Minimum Viable Product).

   Cel Dokumentu:
   Głównym zadaniem niniejszej specyfikacji jest szczegółowe określenie wymagań funkcjonalnych i niefunkcjonalnych aplikacji EchoForm, w tym jej funkcji, ograniczeń, założeń projektowych oraz atrybutów jakościowych. Dokument pełni rolę fundamentu do projektowania architektury, implementacji, testowania oraz oceny końcowej projektu w ramach przedmiotu Inżynieria Oprogramowania.

   Adresaci:
   Dokument jest przeznaczony dla:

   - Członków zespołu projektowego, jako wytyczne do prac deweloperskich
   - Prowadzącego zajęcia, jako podstawa do weryfikacji zakresu i złożoności logicznej projektu

   Sposób Użycia:
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

## 3. Wymagania Funkcjonalne

### 3.1. Priorytetyzacja Wymagań

Aby obiektywnie wybrać zakres funkcjonalności dla wersji MVP, zastosowano model priorytetyzacji oparty na wartości, koszcie i ryzyku.

*Wzór:* Priorytet = (Korzyść + Kara) / (Koszt + Ryzyko)

| Funkcja | Korzyść | Kara | Koszt | Ryzyko | Priorytet |
|---------|---------|------|-------|--------|-----------|
| Anonimowe wypełnianie | 21 | 21 | 8 | 5 | *3.23* |
| Dynamiczne pytania | 21 | 13 | 13 | 8 | *1.62* |
| Tworzenie ankiet | 13 | 13 | 8 | 5 | *2.00* |
| Raporty | 13 | 8 | 8 | 5 | *1.62* |
| Powiadomienia e-mail | 8 | 5 | 5 | 3 | *1.62* |

#### Zakres MVP

Na podstawie analizy do wersji MVP zakwalifikowaliśmy:

- *Anonimowe wypełnianie* (priorytet: 3.23)
- *Tworzenie ankiet* (priorytet: 2.00)
- *Dynamiczną logikę pytań* (priorytet: 1.62)

Funkcje takie jak *powiadomienia e-mail* oraz *podstawowa analiza wyników* uznaliśmy za opcjonalne i mogą zostać dodane w kolejnych iteracjach rozwoju aplikacji.

---

### 3.2. Tworzenie Ankiet

Tytuł: Tworzenie nowej ankiety

Opis: System umożliwia twórcy ankiety tworzenie formularzy zawierających różne typy pytań takie jak: otwarte, zamknięte, jednokrotnego i wielokrotnego wyboru.

Historyjka Użytkownika:
Jako twórca ankiety,  
chcę móc tworzyć własne formularze ankietowe z różnymi typami pytań,  
aby skutecznie zbierać dane od respondentów.


Cel Biznesowy: Zapewnienie elastycznego tworzenia ankiet dopasowanych do różnych potrzeb badawczych i ochrona przed nieuprawnionym dostępem.

Warunki wstępne:
- Użytkownik jest zalogowany w systemie jako twórca ankiety i posiada dostęp do kreatora ankiet

Warunki końcowe:
- Utworzona ankieta zostaje zapisana w systemie i jest gotowa do udostępnienia respondentom

Kryteria akceptacji:

#### WF-ANK-01: Utworzenie ankiety (Scenariusz główny)

- Given: Jestem zalogowanym użytkownikiem z rolą Twórcy Ankiety i posiadam dostęp do kreatora ankiet
- When: Wprowadzam tytuł ankiety oraz dodaję co najmniej jedno pytanie
- Then: Ankieta zostaje zapisana w systemie
- And: System generuje unikalny publiczny link do ankiety

#### WF-ANK-02: Brak tytułu ankiety (Scenariusz alternatywny)

- Given: Jestem zalogowanym twórcą ankiety i znajduję się w kreatorze ankiet
- When: Próbuję zapisać ankietę bez podania tytułu
- Then: System wyświetla komunikat o błędzie informujący o konieczności podania tytułu ankiety
- And: Ankieta nie zostaje zapisana w systemie

---

### 3.3. Dynamiczna Logika Pytań

Tytuł: Warunkowe wyświetlanie pytań

Opis: System wyświetla pytania w zależności od wcześniejszych odpowiedzi respondenta.

Historyjka Użytkownika:
Jako Twórca Ankiety,  
chcę definiować logikę warunkową,  
aby respondenci widzieli tylko istotne pytania.


Cel Biznesowy: Skrócenie ankiety zwiększa wskaźnik jej ukończenia.

Warunki wstępne:
- Ankieta zawiera pytania z regułami warunkowymi

Warunki końcowe:
- Respondent widzi tylko pytania zgodne z logiką jego odpowiedzi w ankiecie

Kryteria akceptacji:

#### WF-DYN-01: Poprawne działanie logiki (Scenariusz główny)

- Given: Ankieta zawiera reguły warunkowe
- When: Respondent udziela odpowiedzi
- Then: System wyświetla właściwe kolejne pytanie, logicznie pasujące do poprzedniego

---

### 3.4. Anonimowe Wypełnianie Ankiety

Tytuł: Wypełnianie ankiety bez logowania

Opis: Respondent może wypełnić ankietę bez zakładania konta.

Historyjka Użytkownika:
Jako Respondent,  
chcę wypełnić ankietę anonimowo,  
aby zachować prywatność.


Cel Biznesowy: Zwiększenie szczerości odpowiedzi.

Warunki wstępne:
- Respondent posiada link lub token

Warunki końcowe:
- Odpowiedzi zostają zapisane w systemie

Kryteria akceptacji:

#### WF-ANO-01: Dostęp przez link (Scenariusz główny)

- Given: Posiadam poprawny link
- When: Otwieram ankietę
- Then: Mogę ją wypełnić

#### WF-ANO-02: Niepoprawny token (Scenariusz alternatywny)

- Given: Token jest nieprawidłowy
- When: Próbuję otworzyć ankietę
- Then: System blokuje dostęp

---

### 3.5. Udostępnianie Ankiet

Tytuł: Kontrola dostępu do ankiety

Opis: Autor może ustawić tryb dostępu: publiczny, prywatny lub z kodem.

Historyjka Użytkownika:
Jako Twórca Ankiety,  
chcę kontrolować dostęp do ankiety,  
aby trafiła do właściwej grupy.


Kryteria akceptacji:

#### WF-ACC-01: Tryb publiczny (Scenariusz główny)

- Given: Ankieta jest publiczna
- When: Udostępniam link
- Then: Każdy ma dostęp

#### WF-ACC-02: Tryb z kodem (Scenariusz alternatywny)

- Given: Ankieta wymaga kodu
- When: Respondent wpisuje poprawny kod
- Then: Uzyskuje dostęp do ankiety

---

### 3.6. Analiza Wyników

Tytuł: Generowanie raportów

Opis: System prezentuje statystyki odpowiedzi.

Historyjka Użytkownika:
Jako Twórca Ankiety,  
chcę zobaczyć raport,  
aby analizować wyniki.


Kryteria akceptacji:

#### WF-RAP-01: Wyświetlanie statystyk (Scenariusz główny)

- Given: Ankieta ma zebrane odpowiedzi
- When: Otwieram raport
- Then: Widzę statystyki (średnie, rozkłady, najczęstsze odpowiedzi)

#### WF-RAP-02: Brak odpowiedzi (Scenariusz alternatywny)

- Given: Ankieta jest pusta (brak odpowiedzi)
- When: Otwieram raport
- Then: System informuje o braku danych

---

### 3.7. Powiadomienia E-mail

Tytuł: Powiadomienia o postępach

Opis: Autor otrzymuje informacje o liczbie odpowiedzi.

Historyjka Użytkownika:
Jako Twórca Ankiety,  
chcę otrzymywać powiadomienia e-mail,  
aby być na bieżąco z postępami zbierania odpowiedzi.


Kryteria akceptacji:

#### WF-MAIL-01: Powiadomienie o nowej odpowiedzi (Scenariusz główny)

- Given: Ankieta jest aktywna
- When: Respondent wypełnia i wysyła ankietę
- Then: Autor otrzymuje powiadomienie e-mail z informacją o nowej odpowiedzi

---

## 4. Atrybuty Jakościowe

| Kategoria | Atrybut | Opis |
|-----------|---------|------|
| Jakość wykonania | Użyteczność (Usability) | Intuicyjność i prostota obsługi systemu dla użytkownika |
| Jakość wykonania | Niezawodność (Reliability) | Jak często system działa poprawnie bez błędów |
| Jakość wykonania | Wydajność (Performance) | Jak szybko system reaguje na działania użytkownika |
| Jakość wykonania | Bezpieczeństwo (Security) | Jak system chroni dane przed nieautoryzowanym dostępem |
| Jakość wykonania | Dostępność (Availability) | Jak często system jest dostępny dla użytkowników |
| Jakość projektu  | Modyfikowalność (Modifiability) | Łatwość wprowadzania zmian w systemie |
| Jakość projektu  | Testowalność (Testability) | Łatwość testowania systemu |
| Jakość projektu  | Przenośność (Portability) | Łatwość przeniesienia systemu na inne środowisko |

### 4.1. Priorytetyzacja Wymagań Jakościowych

*Krok 1: Wybór i priorytetyzacja atrybutów*

Wybrano trzy kluczowe atrybuty jakościowe dla MVP:
- *Użyteczność* - kluczowa dla osiągnięcia wysokiego wskaźnika ukończenia ankiet (KPI)
- *Niezawodność* - błędy w logice dynamicznej prowadzą do porzucania ankiet
- *Wydajność* - wolne działanie zwiększa frustrację użytkowników

*Krok 2: Mierzalna specyfikacja (scenariusze jakościowe)*

*Atrybut 1: Użyteczność (Usability)*
- Źródło bodźca: Respondent
- Bodziec: Rozpoczyna wypełnianie ankiety
- Artefakt: Interfejs ankiety
- Środowisko: Urządzenie mobilne, standardowe obciążenie
- Reakcja: System wyświetla czytelny i prosty formularz
- Miara reakcji: 90% respondentów kończy ankietę bez pomocy

*Atrybut 2: Niezawodność (Reliability)*
- Źródło bodźca: Respondent
- Bodziec: Przechodzi do kolejnego pytania
- Artefakt: Silnik logiki dynamicznej
- Środowisko: Standardowe obciążenie
- Reakcja: System wyświetla poprawne pytanie
- Miara reakcji: 99% przypadków bez błędów logiki

*Atrybut 3: Wydajność (Performance)*
- Źródło bodźca: Respondent
- Bodziec: Otwiera kolejne pytanie
- Artefakt: Backend i interfejs
- Środowisko: Wielu jednoczesnych użytkowników
- Reakcja: System wyświetla pytanie
- Miara reakcji: Czas odpowiedzi < 1 s w 95% przypadków

*Krok 3: Analiza kompromisów architektonicznych*

1. *Użyteczność*  
  Cel: Wysoka intuicyjność interfejsu  
  Możliwe rozwiązanie: Prosty, minimalistyczny UI  
  Kompromisy:  
  - Pozytyw: Większa liczba ukończonych ankiet  
  - Negatyw: Mniejsza elastyczność konfiguracji interfejsu

2. *Niezawodność*  
  Cel: Brak błędów w logice dynamicznej  
  Możliwe rozwiązanie: Rozbudowane testy automatyczne  
  Kompromisy:  
  - Pozytyw: Stabilność systemu  
  - Negatyw: Dłuższy czas wdrożenia

3. *Wydajność*  
  Cel: Czas odpowiedzi < 1 s  
  Możliwe rozwiązanie: Cache i uproszczone zapytania  
  Kompromisy:  
  - Pozytyw: Szybka reakcja systemu  
  - Negatyw: Wyższy koszt infrastruktury, ryzyko nieaktualnych danych