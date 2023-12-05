import networkx as nx
import psycopg2
import matplotlib.pyplot as plt
import matplotlib.colors as mcolors

# Replace the placeholders with your actual database credentials
db_url = "postgresql://postgres:postgres@localhost:5433/search_system_db"

# Connect to the PostgreSQL database using the connection string
conn = psycopg2.connect(db_url)
# Create a cursor
cur = conn.cursor()

# Execute a query to retrieve edges
cur.execute("""SELECT d.title, d2.title
             FROM document_document
                      JOIN public.document d on d.id = document_document.document_id
                      JOIN public.document d2 on d2.id = document_document.related_document_id""")

# Fetch all the rows
edges = cur.fetchall()

# Create a graph
G = nx.DiGraph()
G.add_edges_from(edges)

# Calculate in-degrees of nodes
in_degrees = dict(G.in_degree())

# Calculate node sizes based on in-degrees
node_sizes = [in_degrees[node] * 1500 for node in G.nodes]

# Create a colormap (you can choose a different colormap if desired)
colormap = plt.cm.get_cmap('Greens')

# Normalize the node sizes to fit within the colormap
norm = mcolors.Normalize(vmin=min(node_sizes) / 40, vmax=max(node_sizes) * 2)

# Assign colors to nodes based on in-degrees
node_colors = [colormap(norm(size)) for size in node_sizes]

# Draw the directed graph with arrows and adjusted node sizes
nx.draw(G, with_labels=True, font_weight='bold', arrows=True, node_size=node_sizes, node_color=node_colors, cmap=colormap)
plt.show()

# Close the cursor and connection
cur.close()
conn.close()