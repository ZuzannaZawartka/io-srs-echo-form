# SPECYFIKACJA WYMAGAŃ OPROGRAMOWANIA ECHOFORM (SRS)

## 1. Wstęp

   ### 1.1. Cel, Adresaci i Sposób Użycia

   **Produkt i Wersja:**
   Niniejszy dokument stanowi Specyfikację Wymagań Oprogramowania (SRS) dla systemu **EchoForm** - platformy do anonimowych ankiet z dynamicznymi pytaniami, w wersji 1.0 (MVP - Minimum Viable Product).

   **Cel Dokumentu:**
   Głównym zadaniem niniejszej specyfikacji jest szczegółowe określenie wymagań funkcjonalnych i niefunkcjonalnych aplikacji EchoForm, w tym jej funkcji, ograniczeń, założeń projektowych oraz atrybutów jakościowych. Dokument pełni rolę fundamentu do projektowania architektury, implementacji, testowania oraz oceny końcowej projektu w ramach przedmiotu Inżynieria Oprogramowania.

   **Adresaci:**
   Dokument jest przeznaczony dla:

   - Członków zespołu projektowego, jako wytyczne do prac deweloperskich
   - Prowadzącego zajęcia, jako podstawa do weryfikacji zakresu i złożoności logicznej projektu

   **Sposób Użycia:**
   Specyfikacja powinna być wykorzystywana jako:

   - Punkt odniesienia przy implementacji złożonej logiki biznesowej systemu
   - Kryterium weryfikacji poprawności funkcjonalności podczas fazy testów
   - Podstawa do oceny zgodności systemu z założonymi celami biznesowymi i technicznymi

### 1.2. Wizja, Zakres i Cele Produktu

   #### Wizja
   EchoForm to platforma umożliwiająca tworzenie anonimowych ankiet z inteligentną logiką pytań, które realnie zmniejszają czas potrzebny na ich wypełnienie oraz zwiększają szczegółowość uzyskiwanych odpowiedzi. Dzięki dynamicznemu dostosowywaniu treści, system eliminuje zbędne pytania, co w połączeniu z całkowitym brakiem konieczności logowania i identyfikacji użytkowników, zapewnia pełną szczerość odpowiedzi oraz wyższą jakość danych niż w tradycyjnych, statycznych formularzach.

   #### Zakres
   System umożliwia:

   - *Tworzenie ankiet statycznych oraz dynamicznych* - definiowanie warunkowego wyświetlania pytań. System automatycznie decyduje o ścieżce respondenta na podstawie jego wcześniejszych wyborów
   - *Bezlogowaniowy system dostępu* - zarządzanie uprawnieniami do ankiety poprzez unikalne kody (tokeny), co gwarantuje poufność tożsamości
   - *Warunkowe wyświetlanie pytań* - dynamiczne dostosowywanie formularza do odpowiedzi użytkownika
   - *Udostępnianie arkuszy w trzech trybach* – publicznym (otwarty link), prywatnym (ograniczona lista odbiorców) oraz zabezpieczonym kodem (wymagane hasło lub token)
   - *Analizę wyników* – średnie, rozkłady procentowe, najczęstsze odpowiedzi
   - *Automatyzację powiadomień* - system informowania autora o postępach w zbieraniu wyników (powiadomienia e-mail)

   #### Cele Biznesowe (KPI)

   - *Skuteczność wdrożenia MVP*  
     Umożliwienie Instytucjom Edukacyjnym i Szkoleniowym osiągnięcie wzrostu wskaźnika ukończenia anonimowych ankiet o 25% w ciągu 12 miesięcy, w porównaniu ze standardowymi statycznymi ankietami (z wewnętrznych systemów instytucji)

   - *Skrócenie czasu wypełniania*  
     Zmniejszenie średniego czasu potrzebnego na wypełnienie formularza o 30% dzięki zastosowaniu mechanizmu warunkowego wyświetlania pytań i eliminacji treści nieistotnych dla danego respondenta względem klasycznej statycznej ankiety

   - *Powtarzalność wyników*  
     Wzrost wskaźnika ukończenia o założone 25% musi zostać odnotowany w co najmniej 70% wszystkich przeprowadzonych kampanii ankietowych, co potwierdzi stabilność rozwiązania niezależnie od grupy odbiorców

   #### Poza Zakresem

   - *Konta respondentów* - system nie przewiduje zakładania kont ani profili dla osób udzielających odpowiedzi. Dostęp odbywa się wyłącznie na podstawie tokenów lub linków, bez konieczności rejestracji
   - *Edycja wysłanych odpowiedzi* - po ostatecznym zatwierdzeniu i wysłaniu ankiety przez respondenta, nie będzie możliwości powrotu do formularza w celu zmiany udzielonych odpowiedzi
   - *Komunikator w czasie rzeczywistym* - system nie będzie posiadał modułu czatu ani forum do bezpośredniej komunikacji między autorem ankiety a respondentami
   - *Moduł egzaminacyjny i ocenianie* - aplikacja nie służy do sprawdzania wiedzy. Brak funkcji punktowania odpowiedzi, limitów czasowych na pytanie oraz wystawiania ocen końcowych

   ### 1.3. Definicje i Skróty

   | Termin | Znaczenie |
   |--------|-----------|
   | *SRS* | Specyfikacja Wymagań Oprogramowania |
   | *MVP* | Minimalna wersja produktu (Minimum Viable Product) |
   | *KPI* | Kluczowy wskaźnik efektywności (Key Performance Indicator) |
   | *Respondent* | Osoba wypełniająca ankietę |
   | *Autor* | Twórca ankiety |
   | *Dynamiczne pytania* | Pytania zależne od wcześniejszych odpowiedzi |
   | *Token* | Unikalny kod dostępu do ankiety |
   | *Warunkowe wyświetlanie* | Mechanizm pokazywania pytań w zależności od poprzednich odpowiedzi |