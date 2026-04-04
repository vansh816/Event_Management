//1_code
//@Document(collection = "journal_entries")
//@Data
//public class JournalEntry {//pojo class
//    @Id
//    private ObjectId id;
//    private String title;
//    private String content;
//    private LocalDateTime date;}

//----->controller class
//@RestController
//@RequestMapping("/journal")
//public class JournalEntryControllerV2 {
//    @Autowired
//    private JournalEntryService journalEntryService;

//    @GetMapping
//    public List<JournalEntry> getall(){
//        return journalEntryService.getAll();
//    }
//
//    @PostMapping
//    public JournalEntry createEntry(@RequestBody JournalEntry myEntry){
//        myEntry.setDate(LocalDateTime.now());
//        journalEntryService.saveEntry(myEntry);
//        return myEntry;}
//
//    @PostMapping("/id/{myid}")
//    public JournalEntry getjournalentrybyid(@PathVariable ObjectId myid){
//        return journalEntryService.findbyid(myid).orElse(null);
//    }
//    @DeleteMapping("/id/{myid}")
//    public boolean  deleteJournalentrybyid(@PathVariable ObjectId myid){
//        journalEntryService.deletebyid(myid);
//        return true;
//    }
//    @PutMapping("/id/{id}")
//    public JournalEntry updateJournalentrybyid(@PathVariable ObjectId id,@RequestBody JournalEntry newEntry){
//        JournalEntry old=journalEntryService.findbyid(id).orElse(null);
//        if(old!=null) {
//            old.setTitle(newEntry.getTitle()!= null&& !newEntry.getTitle().equals("")?newEntry.getTitle() : old.getTitle());
//            old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
//        }
//        journalEntryService.saveEntry(old);
//        return  old;
//    }}
//-----<service class
//@Component
//public class JournalEntryService{
//    @Autowired
//    private JournalEntryRepository journalEntryRepository;
//
//    public void saveEntry(JournalEntry journalEntry){
//        journalEntryRepository.save(journalEntry);
//    }
//    public List<JournalEntry> getAll(){
//        return journalEntryRepository.findAll();//predifined method in mongodbrepository
//    }
//    public Optional<JournalEntry> findbyid(ObjectId id){
//        return journalEntryRepository.findById(id);
//    }
 //
//    public void  deletebyid(ObjectId id){
//        journalEntryRepository.deleteById(id);
//    }}
//----->Repository
//@Component
//public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
//MonoRepository->it is a interface which talks with mongodatabase
//<JournalEntry,ObjectId>
//1.JournalEntry->it is simply a pojo class which maps with mongodb as a collection(table).
//2.ObjectId->it is a datatype of a primary key(id) in the class whether it is a Integer,string,ObjectId.
//}
//json->controller->service->mongorepo->use mongo for data

