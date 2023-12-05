import os

def create_html_file(site_name, links):
    file_content = f'''<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>{site_name}</title>
</head>
<body>
    <h1>{site_name}</h1>
'''

    for link in links:
        file_content += f'    <p><a href="http://localhost:8000/{link}.html">Ссылка на сайт {link}</a></p>\n'

    file_content += '''</body>
</html>
'''

    with open(f"{site_name.lower()}.html", "w", encoding="utf-8") as file:
        file.write(file_content)

def main():
    # Define the site structure
    sites = [
        ("Site1", ["Site2", "Site3"]),
        ("Site2", ["Site3", "Site4"]),
        ("Site3", ["Site1"]),
        ("Site4", ["Site1", "Site5"]),
        ("Site5", ["Site6"]),
        ("Site6", ["Site7", "Site8"]),
        ("Site7", ["Site8"]),
        ("Site8", ["Site9"]),
        ("Site9", ["Site10", "Site11"]),
        ("Site10", ["Site11", "Site12"]),
        ("Site11", ["Site12"]),
        ("Site12", ["Site13"]),
        ("Site13", ["Site14"]),
        ("Site14", ["Site15"]),
        ("Site15", ["Site16", "Site17"]),
        ("Site16", ["Site17", "Site18"]),
        ("Site17", ["Site18"]),
        ("Site18", ["Site19"]),
        ("Site19", ["Site20"]),
        ("Site20", ["Site21"]),
        ("Site21", ["Site22"]),
        ("Site22", ["Site23"]),
        ("Site23", ["Site24", "Site25"]),
        ("Site24", ["Site25"]),
        ("Site25", []),
        ("SEO1", ["SEO-main"]),
        ("SEO2", ["SEO-main"]),
        ("SEO3", ["SEO-main"]),
        ("SEO4", ["SEO-main"]),
        ("SEO5", ["SEO-main"]),
        ("SEO-main", []),
    ]

    # Create HTML files for each site
    for site, links in sites:
        create_html_file(site, links)

if __name__ == "__main__":
    main()