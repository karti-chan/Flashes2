package com.example.flashmind.data

/**
 * Provides initial data for the app when the database is empty.
 */
object SampleData {
    fun getInitialSubjects(): List<FlashcardSubject> {
        return listOf(
            FlashcardSubject("phys", "Physics", "Complete formula database"),
            FlashcardSubject("hira", "Japanese Hiragana", "Basic Japanese script")
        )
    }

    fun getInitialSets(): List<FlashcardSet> {
        return listOf(
            // Physics SYMBOLS
            FlashcardSet("p_si", "phys", GroupType.SYMBOLS, "Jednostki SI", "Podstawowe jednostki"),
            FlashcardSet("p_pref", "phys", GroupType.SYMBOLS, "Przedrostki", "Mnożniki jednostek"),
            FlashcardSet("p_const", "phys", GroupType.SYMBOLS, "Stałe Fizyczne", "Wybrane stałe"),
            FlashcardSet("p_astro_sym", "phys", GroupType.SYMBOLS, "Astrofizyka - Stałe", "Parametry astronomiczne"),
            // Physics FLASHCARDS
            FlashcardSet("p_kin", "phys", GroupType.FLASHCARDS, "KINEMATYKA", "Opis ruchu"),
            FlashcardSet("p_forces", "phys", GroupType.FLASHCARDS, "SIŁY TARCIA I SPRĘŻYSTOŚCI", "Tarcia i sprężystość"),
            FlashcardSet("p_dyn", "phys", GroupType.FLASHCARDS, "DYNAMIKA", "Siły i energia"),
            FlashcardSet("p_grav", "phys", GroupType.FLASHCARDS, "GRAWITACJA I ELEMENTY ASTRONOMII", "Pole grawitacyjne i wszechświat"),
            FlashcardSet("p_waves", "phys", GroupType.FLASHCARDS, "DRGANIA I FALE", "Ruch drgający i falowy"),
            FlashcardSet("p_estat", "phys", GroupType.FLASHCARDS, "ELEKTROSTATYKA", "Pola i ładunki"),
            FlashcardSet("p_prad", "phys", GroupType.FLASHCARDS, "PRĄD ELEKTRYCZNY", "Obwody prądu"),
            FlashcardSet("p_magn", "phys", GroupType.FLASHCARDS, "MAGNETYZM", "Pole magnetyczne"),
            FlashcardSet("p_rel", "phys", GroupType.FLASHCARDS, "MECHANIKA RELATYWISTYCZNA", "Einstein"),
            FlashcardSet("p_opt", "phys", GroupType.FLASHCARDS, "OPTYKA GEOMETRYCZNA", "Światło i soczewki"),
            FlashcardSet("p_hydro", "phys", GroupType.FLASHCARDS, "HYDROSTATYKA, AEROSTATYKA", "Płyny i gazy"),
            FlashcardSet("p_term", "phys", GroupType.FLASHCARDS, "TERMODYNAMIKA", "Ciepło i gazy"),
            FlashcardSet("p_atom", "phys", GroupType.FLASHCARDS, "FIZYKA ATOMOWA", "Atomy i kwanty"),
            FlashcardSet("p_misc", "phys", GroupType.FLASHCARDS, "WYBRANE ZALEŻNOŚCI", "Środek masy i wektory"),
            // Hiragana
            FlashcardSet("h_sym", "hira", GroupType.SYMBOLS, "Basic Characters", "Vowels")
        )
    }

    fun getInitialCards(): List<Flashcard> {
        val cards = mutableListOf<Flashcard>()
        
        // Jednostki SI
        cards.add(Flashcard("si1", "p_si", CardType.TEXT, "Metr", "m", 0))
        cards.add(Flashcard("si2", "p_si", CardType.TEXT, "Kilogram", "kg", 1))
        cards.add(Flashcard("si3", "p_si", CardType.TEXT, "Sekunda", "s", 2))
        cards.add(Flashcard("si4", "p_si", CardType.TEXT, "Amper", "A", 3))
        cards.add(Flashcard("si5", "p_si", CardType.TEXT, "Kelwin", "K", 4))
        cards.add(Flashcard("si6", "p_si", CardType.TEXT, "Mol", "mol", 5))
        cards.add(Flashcard("si7", "p_si", CardType.TEXT, "Kandela", "cd", 6))

        // Przedrostki
        cards.add(Flashcard("pr1", "p_pref", CardType.TEXT, "Giga (G)", "10^9", 0))
        cards.add(Flashcard("pr2", "p_pref", CardType.TEXT, "Mega (M)", "10^6", 1))
        cards.add(Flashcard("pr3", "p_pref", CardType.TEXT, "Kilo (k)", "10^3", 2))
        cards.add(Flashcard("pr4", "p_pref", CardType.TEXT, "Mili (m)", "10^{-3}", 3))
        cards.add(Flashcard("pr5", "p_pref", CardType.TEXT, "Mikro (μ)", "10^{-6}", 4))
        cards.add(Flashcard("pr6", "p_pref", CardType.TEXT, "Nano (n)", "10^{-9}", 5))
        cards.add(Flashcard("pr7", "p_pref", CardType.TEXT, "Piko (p)", "10^{-12}", 6))

        // Stałe Fizyczne
        cards.add(Flashcard("sc1", "p_const", CardType.TEXT, "Prędkość światła (c)", "299,792,458 \\text{ m/s}", 0))
        cards.add(Flashcard("sc2", "p_const", CardType.TEXT, "Stała Plancka (h)", "6.626 \\times 10^{-34} \\text{ J}\\cdot\\text{s}", 1))
        cards.add(Flashcard("sc3", "p_const", CardType.TEXT, "Ładunek elementarny (e)", "1.602 \\times 10^{-19} \\text{ C}", 2))
        cards.add(Flashcard("sc4", "p_const", CardType.TEXT, "Stała Boltzmanna (k_B)", "1.38 \\times 10^{-23} \\text{ J/K}", 3))
        cards.add(Flashcard("sc5", "p_const", CardType.TEXT, "Stała grawitacji (G)", "6.674 \\times 10^{-11} \\frac{\\text{N m}^2}{\\text{kg}^2}", 4))
        cards.add(Flashcard("sc6", "p_const", CardType.TEXT, "Przyspieszenie ziemskie (g)", "9.80665 \\text{ m/s}^2", 5))

        // Astrofizyka
        cards.add(Flashcard("as1", "p_astro_sym", CardType.TEXT, "Jednostka astronomiczna (au)", "1.496 \\times 10^{11} \\text{ m}", 0))
        cards.add(Flashcard("as2", "p_astro_sym", CardType.TEXT, "Parsek (pc)", "3.086 \\times 10^{16} \\text{ m}", 1))
        cards.add(Flashcard("as3", "p_astro_sym", CardType.TEXT, "Rok świetlny (ly)", "9.461 \\times 10^{15} \\text{ m}", 2))
        cards.add(Flashcard("as4", "p_astro_sym", CardType.TEXT, "Masa Słońca (M_S)", "1.988 \\times 10^{30} \\text{ kg}", 3))
        cards.add(Flashcard("as5", "p_astro_sym", CardType.TEXT, "Masa Ziemi (M_Z)", "5.972 \\times 10^{24} \\text{ kg}", 4))

        // KINEMATYKA
        cards.add(Flashcard("k1", "p_kin", CardType.TEXT, "Prędkość", "\\vec{v}=\\frac{\\Delta \\vec{r}}{\\Delta t}", 0))
        cards.add(Flashcard("k2", "p_kin", CardType.TEXT, "Przyspieszenie", "\\vec{a}=\\frac{\\Delta \\vec{v}}{\\Delta t}", 1))
        cards.add(Flashcard("k3", "p_kin", CardType.TEXT, "Prędkość kątowa", "\\omega=\\frac{\\Delta\\alpha}{\\Delta t}", 2))
        cards.add(Flashcard("k4", "p_kin", CardType.TEXT, "Związek v i ω", "v=\\omega r", 3))
        cards.add(Flashcard("k5", "p_kin", CardType.TEXT, "Ruch po okręgu", "\\omega=\\frac{2\\pi}{T} \\ ; \\ T=\\frac{1}{f}", 4))
        cards.add(Flashcard("k6", "p_kin", CardType.TEXT, "Przyspieszenie dośrodkowe", "a_{do}=\\frac{v^2}{r}=v\\omega=\\omega^2r", 5))
        cards.add(Flashcard("k7", "p_kin", CardType.TEXT, "Przyspieszenie kątowe", "\\varepsilon=\\frac{\\Delta\\omega}{\\Delta t}", 6))
        cards.add(Flashcard("k8", "p_kin", CardType.TEXT, "Przyspieszenie styczne", "a_{st}=\\varepsilon r", 7))
        cards.add(Flashcard("k9", "p_kin", CardType.TEXT, "Prędkość (ruch jednostajnie zm.)", "\\vec{v}=\\vec{v}_0+\\vec{a}t", 8))
        cards.add(Flashcard("k10", "p_kin", CardType.TEXT, "Droga (ruch jednostajnie zm.)", "s=v_0t+\\frac{1}{2}at^2", 9))

        // SIŁY TARCIA I SPRĘŻYSTOŚCI
        cards.add(Flashcard("f1", "p_forces", CardType.TEXT, "Tarcia kinetycznego", "T_k=\\mu_k F_N", 0))
        cards.add(Flashcard("f2", "p_forces", CardType.TEXT, "Tarcia statycznego", "T_s \\leq \\mu_s F_N", 1))
        cards.add(Flashcard("f3", "p_forces", CardType.TEXT, "Siła sprężystości", "\\vec{F}_s=-k\\vec{x}", 2))
        cards.add(Flashcard("f4", "p_forces", CardType.TEXT, "Energia pot. sprężystości", "E_{pot}=\\frac{1}{2}kx^2", 3))

        // DYNAMIKA
        cards.add(Flashcard("d1", "p_dyn", CardType.TEXT, "Pęd", "\\vec{p}=m\\vec{v}", 0))
        cards.add(Flashcard("d2", "p_dyn", CardType.TEXT, "II zasada dynamiki (układ inercjalny)", "m\\vec{a}=\\vec{F} \\ ; \\ \\frac{\\Delta\\vec{p}}{\\Delta t}=\\vec{F}", 1))
        cards.add(Flashcard("d3", "p_dyn", CardType.TEXT, "Wartość momentu pędu punktu materialnego", "L=rp\\sin\\angle(\\vec{r},\\vec{p})", 2))
        cards.add(Flashcard("d4", "p_dyn", CardType.TEXT, "Wartość momentu siły", "M=rF\\sin\\angle(\\vec{r},\\vec{F})", 3))
        cards.add(Flashcard("d5", "p_dyn", CardType.TEXT, "Moment bezwładności", "I=\\sum_{i=1}^{n}m_ir_i^2", 4))
        cards.add(Flashcard("d6", "p_dyn", CardType.TEXT, "Związek ω i pędu bryły sztywnej", "L=I\\omega", 5))
        cards.add(Flashcard("d7", "p_dyn", CardType.TEXT, "II zasada dynamiki ruchu obrotowego", "I\\varepsilon=M", 6))
        cards.add(Flashcard("d8", "p_dyn", CardType.TEXT, "Praca siły, praca momentu siły", "W_F=F\\Delta r\\cos\\angle(\\vec{F},\\Delta\\vec{r}) \\ ; \\ W_M=M\\Delta\\alpha", 7))
        cards.add(Flashcard("d9", "p_dyn", CardType.TEXT, "Moc", "P=\\frac{W}{\\Delta t}", 8))
        cards.add(Flashcard("d10", "p_dyn", CardType.TEXT, "Energia kinetyczna ruchu postępowego", "E_{kin}=\\frac{1}{2}mv^2", 9))
        cards.add(Flashcard("d11", "p_dyn", CardType.TEXT, "Energia kinetyczna ruchu obrotowego", "E_{kin}=\\frac{1}{2}I\\omega^2", 10))

        // GRAWITACJA
        cards.add(Flashcard("g1", "p_grav", CardType.TEXT, "Prawo powszechnego ciążenia", "F_g=\\frac{Gm_1m_2}{r^2}", 0))
        cards.add(Flashcard("g2", "p_grav", CardType.TEXT, "Natężenie pola grawitacyjnego", "\\vec{\\gamma}=\\frac{\\vec{F}_g}{m} \\ ; \\ \\vec{a}_g=\\vec{\\gamma}", 1))
        cards.add(Flashcard("g3", "p_grav", CardType.TEXT, "Energia potencjalna grawitacji", "E_p=-\\frac{Gm_1m_2}{r}", 2))
        cards.add(Flashcard("g4", "p_grav", CardType.TEXT, "Zmiana energii przy powierzchni Ziemi", "\\Delta E_p=mg\\Delta h", 3))
        cards.add(Flashcard("g5", "p_grav", CardType.TEXT, "Prędkość na orbicie kołowej", "v_{or}=\\sqrt{\\frac{GM}{r}}", 4))
        cards.add(Flashcard("g6", "p_grav", CardType.TEXT, "Prędkość ucieczki", "v_u=\\sqrt{\\frac{2GM}{r}}", 5))
        cards.add(Flashcard("g7", "p_grav", CardType.TEXT, "Orbita eliptyczna - półosie", "a \\text{ -- półoś wielka} \\ ; \\ r_P+r_A=2a", 6))
        cards.add(Flashcard("g8", "p_grav", CardType.TEXT, "II prawo Keplera", "\\frac{\\Delta S}{\\Delta t}=\\mathrm{const} \\ ; \\ \\vec{L}=\\mathrm{const}", 7))
        cards.add(Flashcard("g9", "p_grav", CardType.TEXT, "III prawo Keplera", "\\frac{T_1^2}{a_1^3} = \\frac{T_2^2}{a_2^3} = \\mathrm{const}", 8))
        cards.add(Flashcard("g10", "p_grav", CardType.TEXT, "Prawo Hubble'a", "v=Hd", 9))

        // DRGANIA I FALE
        cards.add(Flashcard("w1", "p_waves", CardType.TEXT, "Ruch harmoniczny (x, v, a)", "x(t)=A\\sin(\\omega t+\\varphi_0) \\ ; \\ v(t)=A\\omega\\cos(\\omega t+\\varphi_0) \\ ; \\ a(t)=-A\\omega^2\\sin(\\omega t+\\varphi_0)", 0))
        cards.add(Flashcard("w2", "p_waves", CardType.TEXT, "Wartości maksymalne (x, v, a)", "x_{\\max}=A \\ ; \\ v_{\\max}=A\\omega \\ ; \\ a_{\\max}=A\\omega^2", 1))
        cards.add(Flashcard("w3", "p_waves", CardType.TEXT, "Siła harmoniczna", "\\vec{F}_h=-m\\omega^2\\vec{x}", 2))
        cards.add(Flashcard("w4", "p_waves", CardType.TEXT, "Częstość (sprężyna i wahadło)", "\\omega=\\sqrt{\\frac{k}{m}} \\ ; \\ \\omega=\\sqrt{\\frac{g}{l}}", 3))
        cards.add(Flashcard("w5", "p_waves", CardType.TEXT, "Energia całkowita oscylatora", "E=\\frac{1}{2}mA^2\\omega^2", 4))
        cards.add(Flashcard("w6", "p_waves", CardType.TEXT, "Parametry ruchu fali", "v=\\frac{\\lambda}{T}=\\lambda f \\ ; \\ T=\\frac{1}{f}", 5))
        cards.add(Flashcard("w7", "p_waves", CardType.TEXT, "Faza fali", "\\varphi(t)=\\frac{2\\pi}{T}t-\\frac{2\\pi}{\\lambda}x+\\varphi_0", 6))
        cards.add(Flashcard("w8", "p_waves", CardType.TEXT, "Wzmocnienie i osłabienie fali", "\\Delta\\varphi=2\\pi n \\ ; \\ \\Delta\\varphi=2\\pi(n+\\frac{1}{2})", 7))
        cards.add(Flashcard("w9", "p_waves", CardType.TEXT, "Natężenie fali", "I=\\frac{E_s}{S\\Delta t} \\ ; \\ I \\sim A^2", 8))
        cards.add(Flashcard("w10", "p_waves", CardType.TEXT, "Natężenie fali kulistej", "I \\sim \\frac{1}{r^2}", 9))
        cards.add(Flashcard("w11", "p_waves", CardType.TEXT, "Załamanie fali", "\\frac{\\sin\\alpha_1}{\\sin\\alpha_2}=\\frac{v_1}{v_2}=\\frac{n_2}{n_1}", 10))
        cards.add(Flashcard("w12", "p_waves", CardType.TEXT, "Efekt Dopplera (przybliżony)", "f_{\\text{ob}} \\approx f_{\\text{źr}}(1 \\pm \\frac{v}{v_d})", 11))
        cards.add(Flashcard("w13", "p_waves", CardType.TEXT, "Efekt Dopplera (ścisły)", "f_{\\text{ob}}=f_{\\text{źr}}\\frac{v_d \\mp v_{\\text{ob}}}{v_d \\pm v_{\\text{źr}}}", 12))
        cards.add(Flashcard("w14", "p_waves", CardType.TEXT, "Siatka dyfrakcyjna", "d\\sin\\alpha_n=n\\lambda", 13))

        // ELEKTROSTATYKA
        cards.add(Flashcard("e1", "p_estat", CardType.TEXT, "Prawo Coulomba", "F_{12}=k\\frac{q_1q_2}{r^2}", 0))
        cards.add(Flashcard("e2", "p_estat", CardType.TEXT, "Natężenie pola", "\\vec{E}=\\frac{\\vec{F}}{q}", 1))
        cards.add(Flashcard("e3", "p_estat", CardType.TEXT, "Napięcie", "U_{AB}=\\frac{W_{AB}}{q}", 2))
        cards.add(Flashcard("e4", "p_estat", CardType.TEXT, "Pojemność kondensatora", "C=\\frac{Q}{U}", 3))
        cards.add(Flashcard("e5", "p_estat", CardType.TEXT, "Energia kondensatora", "W=\\frac{Q^2}{2C}", 4))

        // PRĄD ELEKTRYCZNY
        cards.add(Flashcard("c1", "p_prad", CardType.TEXT, "Natężenie prądu", "I=\\frac{\\Delta Q}{\\Delta t}", 0))
        cards.add(Flashcard("c2", "p_prad", CardType.TEXT, "Opór elektryczny", "R=\\frac{U}{I}", 1))
        cards.add(Flashcard("c3", "p_prad", CardType.TEXT, "Opór właściwy", "R=\\rho\\frac{l}{S}", 2))
        cards.add(Flashcard("c4", "p_prad", CardType.TEXT, "Moc prądu", "P=UI=I^2R=\\frac{U^2}{R}", 3))
        cards.add(Flashcard("c5", "p_prad", CardType.TEXT, "II prawo Kirchhoffa", "\\sum \\mathcal{E}_i - \\sum U_j = 0", 4))

        // MAGNETYZM
        cards.add(Flashcard("m1", "p_magn", CardType.TEXT, "Siła Lorentza", "F=qvB\\sin\\alpha", 0))
        cards.add(Flashcard("m2", "p_magn", CardType.TEXT, "Siła elektrodynamiczna", "F=IlB\\sin\\alpha", 1))
        cards.add(Flashcard("m3", "p_magn", CardType.TEXT, "Strumień magnetyczne", "\\Phi_B=BS\\cos\\alpha", 2))
        cards.add(Flashcard("m4", "p_magn", CardType.TEXT, "SEM indukcji", "\\mathcal{E}=-\\frac{\\Delta\\Phi_B}{\\Delta t}", 3))
        cards.add(Flashcard("m5", "p_magn", CardType.TEXT, "Transformator", "\\frac{U_1}{U_2}=\\frac{N_1}{N_2}", 4))

        // MECHANIKA RELATYWISTYCZNA
        cards.add(Flashcard("r1", "p_rel", CardType.TEXT, "Energia całkowita", "E=\\frac{mc^2}{\\sqrt{1-\\frac{v^2}{c^2}}}", 0))
        cards.add(Flashcard("r2", "p_rel", CardType.TEXT, "Energia spoczynkowa", "E_0=mc^2", 1))
        cards.add(Flashcard("r3", "p_rel", CardType.TEXT, "Pęd relatywistyczny", "p=\\frac{mv}{\\sqrt{1-\\frac{v^2}{c^2}}}", 2))

        // OPTYKA
        cards.add(Flashcard("o1", "p_opt", CardType.TEXT, "Kąt graniczny", "\\sin\\alpha_{gr}=\\frac{n_1}{n_2}", 0))
        cards.add(Flashcard("o2", "p_opt", CardType.TEXT, "Warunek polaryzacji przy odbiciu", "\\alpha_{\\text{pad}}+\\alpha_{\\text{zał}}=90^\\circ", 1))
        cards.add(Flashcard("o3", "p_opt", CardType.TEXT, "Równanie soczewki i zwierciadła", "\\frac{1}{x}+\\frac{1}{y}=\\frac{1}{f} \\ ; \\ x>0", 2))
        cards.add(Flashcard("o4", "p_opt", CardType.TEXT, "Położenie obrazu (y)", "y>0 \\text{ -- rzeczywisty} \\ ; \\ y<0 \\text{ -- pozorny}", 3))
        cards.add(Flashcard("o5", "p_opt", CardType.TEXT, "Ogniskowa (f)", "f>0 \\text{ -- skupiające} \\ ; \\ f<0 \\text{ -- rozpraszające}", 4))
        cards.add(Flashcard("o6", "p_opt", CardType.TEXT, "Wzór na ogniskową soczewki", "\\frac{1}{f}=\\left(\\frac{n_{\\text{socz}}}{n_{\\text{otocz}}}-1\\right)\\left(\\pm\\frac{1}{R_1}\\pm\\frac{1}{R_2}\\right)", 5))

        // HYDROSTATYKA
        cards.add(Flashcard("h1", "p_hydro", CardType.TEXT, "Siła parcia i ciśnienie", "F=p\\Delta S \\ ; \\ \\vec{F}\\perp\\Delta S", 0))
        cards.add(Flashcard("h2", "p_hydro", CardType.TEXT, "Zmiana ciśnienia", "\\Delta p=\\rho g\\Delta h", 1))
        cards.add(Flashcard("h3", "p_hydro", CardType.TEXT, "Siła wyporu", "F_{\\text{wyp}}=\\frac{Q^2}{2C} = \\rho V_{\\text{zan}}g", 2))
        cards.add(Flashcard("h4", "p_hydro", CardType.TEXT, "V_zan - definicja", "V_{\\text{zan}} \\text{ -- objętość części ciała}", 3))
        cards.add(Flashcard("h5", "p_hydro", CardType.TEXT, "ρ - definicja", "\\rho \\text{ -- gęstość cieczy (lub gazu)}", 4))

        // TERMODYNAMIKA
        cards.add(Flashcard("t1", "p_term", CardType.TEXT, "I zasada termodynamiki", "\\Delta U=Q+W", 0))
        cards.add(Flashcard("t2", "p_term", CardType.TEXT, "Praca siły parcia (p=const)", "|W|=p|\\Delta V|", 1))
        cards.add(Flashcard("t3", "p_term", CardType.TEXT, "Związek pracy z polem p(V)", "|W_{AB}| = \\text{pole pod wykresem } AB", 2))
        cards.add(Flashcard("t4", "p_term", CardType.TEXT, "Ciepło właściwe", "c_w=\\frac{Q}{m\\Delta T}", 3))
        cards.add(Flashcard("t5", "p_term", CardType.TEXT, "Ciepło molowe", "C=\\frac{Q}{n\\Delta T}", 4))
        cards.add(Flashcard("t6", "p_term", CardType.TEXT, "Ciepło przemiany fazowej", "L=\\frac{Q}{m}", 5))
        cards.add(Flashcard("t7", "p_term", CardType.TEXT, "Średnia energia ruchu cząsteczek", "E_{\\text{śr}}=\\frac{3}{2}kT", 6))
        cards.add(Flashcard("t8", "p_term", CardType.TEXT, "Równanie Clapeyrona", "pV=nRT", 7))
        cards.add(Flashcard("t9", "p_term", CardType.TEXT, "Związek ciepła molowego", "C_p=C_v+R", 8))
        cards.add(Flashcard("t10", "p_term", CardType.TEXT, "Cykl silnika (całk. ciepło i praca)", "0=Q_{\\text{całk}}+W_{\\text{całk}}", 9))
        cards.add(Flashcard("t11", "p_term", CardType.TEXT, "Sprawność silnika cieplnego", "\\eta = \\frac{|W_{\\text{całk}}|}{|Q_{\\text{pob}}|} = \\frac{|Q_{\\text{pob}}|-|Q_{\\text{odd}}|}{|Q_{\\text{pob}}|}", 10))

        // FIZYKA ATOMOWA
        cards.add(Flashcard("a1", "p_atom", CardType.TEXT, "Energia fotonu", "E_f=hf=\\frac{hc}{\\lambda}", 0))
        cards.add(Flashcard("a2", "p_atom", CardType.TEXT, "Zjawisko fotoelektryczne", "E_f=W_{el}+E_{kin,max}", 1))
        cards.add(Flashcard("a3", "p_atom", CardType.TEXT, "Poziomy energii (H)", "E_n=-\\frac{13.6 \\text{ eV}}{n^2}", 2))
        cards.add(Flashcard("a4", "p_atom", CardType.TEXT, "Prawo rozpadu", "N(t)=N_0(\\frac{1}{2})^{\\frac{t}{T}}", 3))

        // WYBRANE ZALEŻNOŚCI
        cards.add(Flashcard("mi1", "p_misc", CardType.TEXT, "Środek masy", "\\vec{r}_{SM}=\\frac{\\sum m_i\\vec{r}_i}{\\sum m_i}", 0))
        cards.add(Flashcard("mi2", "p_misc", CardType.TEXT, "Droga (pole pod v(t))", "S_{AB}=\\text{pole pod wykresem } AB", 1))

        // Hiragana
        cards.add(Flashcard("h1", "h_sym", CardType.TEXT, "あ", "a", 0))
        cards.add(Flashcard("h2", "h_sym", CardType.TEXT, "い", "i", 1))

        return cards
    }
}
