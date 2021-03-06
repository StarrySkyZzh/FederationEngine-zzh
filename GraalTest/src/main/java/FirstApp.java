import fr.lirmm.graphik.graal.api.core.ConjunctiveQuery;
import fr.lirmm.graphik.graal.api.kb.KnowledgeBase;
import fr.lirmm.graphik.graal.io.dlp.DlgpParser;
import fr.lirmm.graphik.graal.io.dlp.DlgpWriter;
import fr.lirmm.graphik.graal.kb.KBBuilder;
import fr.lirmm.graphik.util.stream.CloseableIterator;

public class FirstApp {
    public static void main(String[] args) throws Exception {
        // 0 - Create a KBBuilder
        KBBuilder kbb = new KBBuilder();
        // 1 - Add a rule
        kbb.add(DlgpParser.parseRule("mortal(X) :- human(X)."));
        // 2 - Add a fact
        kbb.add(DlgpParser.parseAtom("human(socrate)."));
        // 3 - Generate the KB
        KnowledgeBase kb = kbb.build();
        // 4 - Create a DLGP writer to print data
        DlgpWriter writer = new DlgpWriter();
        // 5 - Parse a query from a Java String
        ConjunctiveQuery query = DlgpParser.parseQuery("?(X) :- mortal(X).");
        // 6 - Query the KB
        CloseableIterator resultIterator = kb.query(query);
        // 7 - Iterate and print results
        writer.write("\n= Answers =\n");
        if (resultIterator.hasNext()) {
            do {
                writer.write(resultIterator.next());
                writer.write("\n");
            } while (resultIterator.hasNext());
        } else {
            writer.write("No answers.\n");
        }
        // 8 - Close resources
        kb.close();
        writer.close();
    }
}
