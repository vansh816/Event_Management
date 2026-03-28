//------>USERS entity
//@Document(collection = "users")
//@Data
//public class User{
//    @Id
//    private ObjectId id;
//    @Indexed(unique = true)
//    @NotNull
//    private String userName;
//    @NotNull
//    private String password;
//  -----  @DBRef
//  -----  public List<JournalEntry> journalEntries=new ArrayList<>();}

//------> USERS controller
//@RestController
//@RequestMapping("/UserController2")
//public class UserController2 {
//    @Autowired
//    private UserService userService;
//    @GetMapping
//    public List<User> getAllUsers(){
//        return userService.getAll();
//    }
//    @PostMapping
//    public void createUser(@RequestBody User UserController2){
//        userService.saveEntry(UserController2);
//    }
//    @PutMapping
//    public ResponseEntity<?> updateuser(@RequestBody User UserController2,@String userName){
//        User userInDb=userService.findByUserName(userName());
//        if(userInDb!=null){
//            userInDb.setUserName(UserController2.getUserName());
//            userInDb.setPassword(UserController2.getPassword());
//            userService.saveEntry(userInDb);
//            return new ResponseEntity<>(userInDb, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }}

//------> USERS service
//@Component
//public class UserService {
//    @Autowired
//    private UserRepository userRepository;
//
//    public List<User> getAll(){
//        return userRepository.findAll();//predifined method in mongodbrepository
//    }
//    public Optional<User> findByid(ObjectId id){
//        return userRepository.findById(id);
//    }
//    public void  deleteByid(ObjectId id){
//        userRepository.deleteById(id);
//    }
//    public User findByUserName(String userName){
//        return userRepository.findByUserName(userName);
//    }
//    public void saveEntry(User UserController2) {
//        userRepository.save(UserController2); }}

//-------> USERS repository
//@Component
//public interface UserRepository extends MongoRepository<User, ObjectId> {
//    User findByUserName(String username);
//}
//--------------------------------------------------------------------------------------

//-----> JOURNAL_ENTRIES entity
//@Document(collection = "journal_entries")
//@Data
//public class JournalEntry {//pojo class
//    @Id
//    private ObjectId id;
//    @NotNull
//    private String title;
//    private String content;
//    private LocalDateTime date;}

//------>JOURNAL_ENTRIES controller
//@RestController
//@RequestMapping("/journal")
//public class JournalEntryControllerV2 {
//    @Autowired
//    private JournalEntryService journalEntryService;
//
//    @GetMapping
//    public ResponseEntity<?> getall(){
//        List<JournalEntry> all=journalEntryService.getAll();
//        if(all!=null && !all.isEmpty()){
//            return  new ResponseEntity<>(all, HttpStatus.OK);
//        }
//        return  new ResponseEntity<>(all,HttpStatus.NOT_FOUND);
//    }
//    @PostMapping
//    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
//        try{
//            myEntry.setDate(LocalDateTime.now());
//            journalEntryService.saveEntry(myEntry);
//            return  new ResponseEntity<>(myEntry,HttpStatus.OK);
//        }
//        catch(Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }}
//    @GetMapping ("/id/{myid}")
//    public ResponseEntity<JournalEntry> getjournalentrybyid(@PathVariable ObjectId myid){
//        Optional<JournalEntry> journalEntry = journalEntryService.findbyid(myid);
//        if(journalEntry.isPresent()){
//            return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//    @DeleteMapping("/id/{myid}")
//    public ResponseEntity<?> deleteJournalentrybyid(@PathVariable ObjectId myid){
//        journalEntryService.deletebyid(myid);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//    @PutMapping("/id/{id}")
//    public ResponseEntity<JournalEntry> updateJournalentrybyid(@PathVariable ObjectId id,@RequestBody JournalEntry newEntry){
//        JournalEntry old=journalEntryService.findbyid(id).orElse(null);
//        if(old!=null) {
//            old.setTitle(newEntry.getTitle()!= null&& !newEntry.getTitle().equals("")?newEntry.getTitle() : old.getTitle());
//            old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
//            journalEntryService.saveEntry(old);
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);}}

//------> JOURNAL_ENTRIES service
//@Component
//public class JournalEntryService {
//
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
//    public void  deletebyid(ObjectId id){
//        journalEntryRepository.deleteById(id);
//    }}


//------->JOURNAL_ENTRIES repository
//@Component
//public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
//MonoRepository->it is a interface which talks with mongodatabase
//<JournalEntry,ObjectId>
//1.JournalEntry->it is simply a pojo class which maps with mongodb as a collection(table).
//2.ObjectId->it is a datatype of a primary key(id) in the class whether it is a Integer,string.

//json->controller->service->mongorepo->use mongo for data
//}


