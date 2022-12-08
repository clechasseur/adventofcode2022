package io.github.clechasseur.adventofcode.y2022.data

object Day8Data {
    val input = """
        101002200302221033023303143134003020403131311534441424251301311211314032120023113231033031121022011
        000000201330102222312340313324430424413214435242113342242434514432041413213343304030120232213020102
        010000203232213031433114342021403232235333514333553111322134215441434143114041342040010222003200011
        201120030122210332140433403113122131244442241135435342113515341543341343142312131142111133212030212
        102222201221120143442131002141522315514253432414212415252133341422341353034001030100033222300111111
        121102223123133443003222043412143224143335142344513313134225453143435255411141013220230131131103220
        001113200123030444213411141214335523335333534154243113221355254515244442254124002044421400233210010
        110103213102123332202242154123512313351111234166565435351154333252135532152130134301110311011211302
        202210200232024303040121311534141553542265444265636563223452433241214155443145244322333331220130021
        311321020104411241422115232552455355223632332662654636255644333321432523545314210123114112022121202
        222002132341041021001451442245143233564442235333245652526562422434515153555514555434223101320132320
        012330123123141204422541513243454542553522546663646433355336545446455311231412535553131322133211112
        221020042122012412514131344114346532626463563556224633256642443366425455121512441453010231033211133
        332033424242124441354515425455263244464236244225445544325265663643264335515552445211224403010100310
        030311420043214251151555343264543446453446552646733662422253465425563224633342525545450000211134232
        133130242134014131114123322632555364662426753737446646475562363245344344452314544451115100103124211
        010241031411233332141421664544623522355767756747554554747757666264354245633431233234531112142010000
        032200414320445341121222366366536655363775663475543654367763436632366656252654425223554320030244023
        100312204312544442231224422656636343643666666675437535763634446467552325246643265513544155011313414
        224200324014345533246524254552547477646677554437567554337355755446755334623236365331343231024211422
        033313133343534535442526235566556757756376445547773754345356657367535536454223645252453342101312232
        014214311423213321245644363264673667435545337543647756777577353345733666342643335553134311523334310
        433012115243133522264542435556563337454353635845778758545734666374637354763652326265532341134234010
        122104225233125212546633243745466643765468465558687657485774737446654743732535366535234121125424032
        204422031225321235542565463563676654365748455655554855875447656357557557553564355522251545122120414
        104030155235414325532425576445546567668547857674866766557768587675345755454524663452432145531304002
        433201242123126354333256673757544348684646787646657846685878846678477736547744545443362515111430002
        421041533111312262353644534644765667864485488887876654464654665486755377755356532333562554413540432
        342132214445526355546434337565344654655444658854445646855468648464874347744635352463235535235421003
        222432351445342522443766645456676656776867887889896877875754784787546635745633765324264433333551223
        341212355142466246464534667453446875455885986995966865976564675747584763576337373532355453154423412
        414423452431252544553637745358445755855869665699958857986985775584484587767664443655566454352331120
        331215421413552422267473475374764764685968687855585566867657585477656745667376557663644634513533254
        141521331136665233544534743877458854586759789577787777878566877976454647447747733666555522535352251
        402144545565566445336477735645686455987955977976986897797959998564448556455676776442342252552521451
        322442131336445645474737578875588748678578675776975869885577996779444458486775646674445543542124334
        003241123534254267466637376887768457676676697687767866888676895686968878848835646576663666545432111
        234333144534365657733346788857655988779985696798896978698598888876655458486736676576434555325322325
        033552244452442344756445574878458788978579767688788877779988976889865655445866355444755456364252412
        045212224265223274466546745678787779868966769796779996688776788997588766768566653733542466332343212
        022345335335622575577377665548779887775887668866967689898976679598597977564865565676552665532252433
        124424326262234276464756765868756565759887879677666689797988796776968864654566663767726322324124335
        333235323262262774735557685448988678797797668789888699889789979858656855774878653676666634655531341
        424333343566654656357438457485875989579778669668898998866878867779978654788886774367636526533252225
        415125222443226566336488555748579996699698777779888777798979989765676998674856437637353652266454354
        352445544426323443664578765568777867798698798797898998876679686986759578786745736574633332255424325
        234414146454465754353766676787665695889898688889799998979699988898879958587847734733456456546445213
        253423343266262636346545784545955997786868689787777888877989878667585988554475745545464666246345255
        223344452632624554335577685647698768667799967878779989777676967798899578558744854574747232646433315
        323143554223367457566474647879967975786699778989879879989898896767757786678775547767465565642312542
        453333425665457354663758546689578757986968687979879987979786897878578778558764873445465456444554251
        155241123442465734343774685765569588776788977988999898777967666866598756658647635644333635325524324
        133324323353235556775356684478588589688899969988787797978967799889667778488858574745533345265415432
        133433465463254577643484465878597856769979997797877989877976896969996799855585466476573354552411331
        322142442345334767374787688576777897796868768989777997787767697699997566868745535447346265543441455
        334123333556324657343574547669589758777898966977799899896987686797899798466557773536632423622355555
        252411345622435455733376748766597586798899988899787799866969666997598966844867765636372634226515431
        151451422566464676476636547768757596689778699796978997868699677765865767487847755454345544253541243
        241522424344532733447477666686899766556969789978797986887979976685588974646546376573463425453144442
        414153442333535675773778676465857568699869669979798878799699987555578544654866655766355464256143434
        254535114546225443653565564468588956985676686799687868668969677759799544785465437745366532254235544
        242354325525625246745576475877646586859558986979977676788989969868686685585574645776626326624152314
        311534354442664567445645456546887875578685886896868888778995555587868655487466666734642465562551342
        002211144542443356445646675767857767788697589869678789696659999887964585784844754653622362431441151
        321444222136434263753573364864445668989879955758697979559578965668845546758456377667636425635512435
        414532112334525645777645364755584566557779787975679695558987865866785684465356447773463436335142124
        031344233134526256667673555464868564656878988979998868666676959687487848847374753332446352421433230
        033414314236345666365347334576457447479979876959976796697989878876845844877346675432432545345335111
        223234514233244546235744434764588774445585595867867967979958876756686776747444433756462224514313130
        034354325554623636456367743544848648668869978989756578576878558576448675345736576332464452532335404
        333314415414336544645473564754765775745445998599878869766776785445665883745666543445236422534254343
        010124352335143245623535734775575868768457445896887988785744846678545657565366664436462333352114420
        241133243154345423423255773635648554845867764484448485764485687547646634676644634466423212541145312
        144323242255213222655427634775767456477656885488677655667865776854846364533353433622362243343123130
        300121513332133346652524546544554657846788487754577484485476445856537556455366326344443253531244041
        214312313412252446224434245656574644854775458585756885886757548546376564453624436535431411344442441
        344413254252322432546665356466536346455488854547546677556747685543743366537445433643315232412504023
        030420125114122234256653334466366437674664466777764545468744766663566333665553255266154254345444421
        201022041343111332264235342277534444744733675444554744888635745554563756363264366244234521125431143
        310101422412112412564453556665535356375565736473373764446357574757477663532553444551433143140032240
        012314020152242145455646464446435574447466754663444663635667635447474466354626635343323553204203443
        301431002405324343435445225364363665573647453443457376344534655734744645626433554455413541101031210
        220402234402321244123522323565664673457656366554777543476673677437665464342236224222241525244012343
        220413243141534321333134422233522646445365544774576465633777457645452462264364422521511214321414141
        132141004144343445425341422356543254466645567446756557773334777235533435444561522153115533032340122
        103320433201024354323412453646353445225544357655543544744544433555545632335324333523314131010244103
        120000103420114333412252226262424253356432654542437554352625536654452456453415255241241141213210301
        230111133023321345242332554223645255622442243546353532232263224265253656423424442512332241113030012
        012002204104404423142222435245362563453526446562334552352242626533532663145221123423411140404113320
        133132301104434132213122523451216645666342442525664663356233663355434224331132321242424042020300010
        300231011102122014443143353352244166655656365456622444622563466362355333112242211003402201113312131
        131113313033421421004254454232251141536335223326242246444432526552154551545414220312100320203333130
        003102322130011220131211452522433142255236443652565643264342211255545111112515532343240204112100111
        123133331310024423412433454313542233234224542223452553553512331141534342314224343121244143312101102
        122232320002101404113223133225112114533212125134253312412213451454333424514233200042430112130222232
        011003112002012034340041324422224354112355545251413535512312452115342144411141020000324121133331211
        110222203303002103314434412231554151352351455233215445523441215221313240230030321334321131320033200
        110202122210001303002442001413413542523211141353543531115315334551133023131104242032323233333112222
        110120101211020201230001300132340142214214554553131253415234133311121310214320100143302113213302021
    """.trimIndent()
}
