package org.inex.Model;

import java.util.ArrayList;

import org.inex.Utils.UtilArticleLink;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

/****************/
/** GraphLink  **/
/****************/

public class GraphLink {

    // Variables
    private Graph<String, DefaultWeightedEdge> multiGraph;

    private int totalNbVertex; // Number of articles / Number of referenced xml files

    private int totalNbIncomingLink; // In-degree / Liens entrants

    private int totalNbOutgoingLink; // Out-degree / Liens sortants

    private ArrayList<ArticleVertex> articleVertexList;

    // Constructors

    public GraphLink() {

    }

    public GraphLink(ArrayList<Doc> docList) {

        // Build the list of all xml files
        ArrayList<String> articleList = UtilArticleLink.createArticleList(docList);

        // Create, init the graph with each article as a vertex and compute the size of the graph
        this.multiGraph = UtilArticleLink.createGraph();
        this.multiGraph = UtilArticleLink.fillGraphVertex(this.multiGraph, articleList);
        this.totalNbVertex = this.multiGraph.vertexSet().size();
        this.totalNbIncomingLink = 0;
        this.totalNbOutgoingLink = 0;

        // Init ArticleVertexList of the graph
        this.articleVertexList = new ArrayList<>();

        // We build the list of linked article lists
        docList.forEach(doc -> {
            ArrayList<String> linkedArticleList = new ArrayList<>();
            linkedArticleList = UtilArticleLink.getAllLinkedArticle(doc.getLinks());
            this.multiGraph = UtilArticleLink.fillArticleGraphEdge(this.multiGraph, doc.getId(), linkedArticleList);
        });

        // We compute the graph degrees
        docList.forEach(doc -> {
            String idDoc = doc.getId();
            int inDegree = this.multiGraph.inDegreeOf(idDoc);
            int outDegree = this.multiGraph.outDegreeOf(idDoc);
            double popularity = (double) inDegree / (docList.size() - 1);
            articleVertexList.add(new ArticleVertex(idDoc, inDegree, outDegree, popularity));
        });

    }

    // Getters && Setters
    public Graph<String, DefaultWeightedEdge> getMultiGraph() {
        return multiGraph;
    }

    public void setMultiGraph(Graph<String, DefaultWeightedEdge> multiGraph) {
        this.multiGraph = multiGraph;
    }

    public int getTotalNbVertex() {
        return totalNbVertex;
    }

    public void setTotalNbVertex(int totalNbVertex) {
        this.totalNbVertex = totalNbVertex;
    }

    public int getTotalNbIncomingLink() {
        return totalNbIncomingLink;
    }

    public void setTotalNbIncomingLink(int totalNbIncomingLink) {
        this.totalNbIncomingLink = totalNbIncomingLink;
    }

    public int getTotalNbOutgoingLink() {
        return totalNbOutgoingLink;
    }

    public void setTotalNbOutgoingLink(int totalNbOutgoingLink) {
        this.totalNbOutgoingLink = totalNbOutgoingLink;
    }

    public ArrayList<ArticleVertex> getArticleVertexList() {
        return articleVertexList;
    }

    public void setArticleVertexList(ArrayList<ArticleVertex> articleVertexList) {
        this.articleVertexList = articleVertexList;
    }

    /********************/
    /** ArticleVertex **/
    /********************/

    public class ArticleVertex {

        // Variables
        private String id;

        private int incomingLink; // In-degrees / Liens entrants

        private int outgoingLink; // Out-degrees / Liens sortants

        private double popularity; // Need to be computed

        // Constructors
        public ArticleVertex() {

        }

        public ArticleVertex(String id) {
            this.id = id;
            this.incomingLink = 0;
            this.outgoingLink = 0;
            this.popularity = 0;
        }

        public ArticleVertex(String id, int incomingLink, int outgoingLink, double popularity) {
            this.id = id;
            this.incomingLink = incomingLink;
            this.outgoingLink = outgoingLink;
            this.popularity = popularity;
        }

        // Getters && Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIncomingLink() {
            return incomingLink;
        }

        public void setIncomingLink(int incomingLink) {
            this.incomingLink = incomingLink;
        }

        public int getOutgoingLink() {
            return outgoingLink;
        }

        public void setOutgoingLink(int outgoingLink) {
            this.outgoingLink = outgoingLink;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        @Override
        public String toString() {
            return "ArticleVertex [id=" + id + ", incomingLink=" + incomingLink + ", outgoingLink=" + outgoingLink
                    + "]";
        }

    }

}
